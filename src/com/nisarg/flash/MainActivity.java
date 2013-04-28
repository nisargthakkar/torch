package com.nisarg.flash;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	Camera cam;
	Parameters camParams;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		try{
			cam=Camera.open();
			camParams = cam.getParameters();
			camParams.setFlashMode(Parameters.FLASH_MODE_TORCH);
			cam.setParameters(camParams);
            cam.startPreview();
		}catch (Exception e) {
            e.printStackTrace();
            cam.stopPreview();
        }
		
		ToggleButton tbTorch=(ToggleButton)findViewById(R.id.tbTorch);		
		tbTorch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (((ToggleButton) v).isChecked()){
					try{
						camParams = cam.getParameters();
						if(camParams.getFlashMode()!=Parameters.FLASH_MODE_TORCH){
							camParams.setFlashMode(Parameters.FLASH_MODE_TORCH);
							cam.setParameters(camParams);
				            cam.startPreview();
						}
					}catch (Exception e) {
			            e.printStackTrace();
			            cam.stopPreview();
			        }
				}
				
				else{
					if (camParams.getFlashMode()!=Parameters.FLASH_MODE_OFF){
						camParams.setFlashMode(Parameters.FLASH_MODE_OFF);
						cam.setParameters(camParams);
						}
				}
			}
		});
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		if (camParams.getFlashMode()!=Parameters.FLASH_MODE_OFF){
			camParams.setFlashMode(Parameters.FLASH_MODE_OFF);
			cam.setParameters(camParams);
		}
		cam.stopPreview();
		cam.release();
	}

}
