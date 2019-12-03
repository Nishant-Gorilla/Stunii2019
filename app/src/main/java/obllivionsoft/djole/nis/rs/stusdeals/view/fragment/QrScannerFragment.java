package obllivionsoft.djole.nis.rs.stusdeals.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrScannerFragment extends Fragment {

    private static final int REQUEST_CAMERA = 1;
    public ZXingScannerView mScannerView;

    public QrScannerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        if(mScannerView == null)
            mScannerView = new ZXingScannerView(getActivity());

//        mScannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
//            @Override
//            public void handleResult(Result result) {
//
//                Log.v("asd", result.getText()); // Prints scan results
//                Log.v("asd", result.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
//
//                // If you would like to resume scanning, call this method below:
//                mScannerView.resumeCameraPreview(this);
//            }
//        });
//        mScannerView.startCamera(); //pokrece kameru

        return mScannerView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            mScannerView.stopCamera();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
