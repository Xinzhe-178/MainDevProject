package com.example.lib_easyphotos.utils.result;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lib_easyphotos.EasyPhotos;
import com.example.lib_easyphotos.callback.PuzzleCallback;
import com.example.lib_easyphotos.callback.SelectCallback;
import com.example.lib_easyphotos.engine.ImageEngine;
import com.example.lib_easyphotos.models.album.entity.Photo;
import com.example.lib_easyphotos.ui.EasyPhotosActivity;
import com.example.lib_easyphotos.ui.PuzzleActivity;

import java.util.ArrayList;


/**
 * HolderFragment
 *
 * @author joker
 * @date 2019/4/9.
 */
public class HolderFragment extends Fragment {

    private static final int HOLDER_SELECT_REQUEST_CODE = 0x44;
    private static final int HOLDER_PUZZLE_REQUEST_CODE = 0x55;
    private SelectCallback mSelectCallback;
    private PuzzleCallback mPuzzleCallback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void startEasyPhoto(SelectCallback callback) {
        mSelectCallback = callback;
        EasyPhotosActivity.start(this, HOLDER_SELECT_REQUEST_CODE);
    }

    public void startPuzzleWithPhotos(ArrayList<Photo> photos, String puzzleSaveDirPath, String puzzleSaveNamePrefix, boolean replaceCustom, @NonNull ImageEngine imageEngine, PuzzleCallback callback) {
        mPuzzleCallback = callback;
        PuzzleActivity.startWithPhotos(this, photos, puzzleSaveDirPath, puzzleSaveNamePrefix, HOLDER_PUZZLE_REQUEST_CODE, replaceCustom, imageEngine);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case HOLDER_SELECT_REQUEST_CODE:
                    if (mSelectCallback != null) {
                        ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
                        boolean selectedOriginal = data.getBooleanExtra(EasyPhotos.RESULT_SELECTED_ORIGINAL, false);
                        mSelectCallback.onResult(resultPhotos, selectedOriginal);
                    }
                    break;
                case HOLDER_PUZZLE_REQUEST_CODE:
                    if (mPuzzleCallback != null) {
                        Photo puzzlePhoto = data.getParcelableExtra(EasyPhotos.RESULT_PHOTOS);
                        mPuzzleCallback.onResult(puzzlePhoto);
                    }
                    break;
            }
            return;
        }
        if (Activity.RESULT_CANCELED == resultCode) {
            switch (requestCode) {
                case HOLDER_SELECT_REQUEST_CODE:
                    if (mSelectCallback != null) {
                        mSelectCallback.onCancel();
                    }
                    break;
                case HOLDER_PUZZLE_REQUEST_CODE:
                    if (mPuzzleCallback != null) {
                        mPuzzleCallback.onCancel();
                    }
                    break;
            }
        }
    }
}
