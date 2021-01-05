package com.opensource.beacon.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.opensource.beacon.Constants;
import com.opensource.beacon.R;

import java.util.HashMap;
import java.util.Map;


public class QRCodeDialogFragment extends DialogFragment {


    private ImageView mIvQRCode;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    public static QRCodeDialogFragment newInstance() {
        return new QRCodeDialogFragment();
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_qr_code, null);

        mIvQRCode = view.findViewById(R.id.image_view_qr_code);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogFragmentPopUp);

        generateQRCode();

        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void generateQRCode() {
        String text = String.valueOf(System.currentTimeMillis());
        try {
            Bitmap bitmap = textToImageEncode(text);
            mIvQRCode.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap textToImageEncode(String Value) throws WriterException {

        Map<EncodeHintType, Object> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.MARGIN, new Integer(0));
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.QR_CODE,
                    Constants.QR_CODE_DIMENSION, Constants.QR_CODE_DIMENSION, hintMap
            );
        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];


        int color_black = getResources().getColor(R.color.black);
        int color_white = getResources().getColor(R.color.white);

        for (int y = 0; y < bitMatrixHeight; y++) {

            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ? color_black : color_white;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 1000, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }
}
