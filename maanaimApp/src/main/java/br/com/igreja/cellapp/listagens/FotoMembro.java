package br.com.igreja.cellapp.listagens;

import br.com.igreja.cellapp.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Base64;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

public class FotoMembro extends Activity{

	private static final String TAG = "Touch";

	//These matrices will be used to move and zoom image
	Matrix matrix = new Matrix();
	Matrix savedMatrix = new Matrix();  

	// We can be in one of these 3 states
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	static final int DRAW =3;
	int mode = NONE;

	// Remember some things for zooming
	PointF start = new PointF();
	PointF mid = new PointF();
	float oldDist = 1f;

	// Limit zoomable/pannable image
	private float[] matrixValues = new float[9];
	private float maxZoom;
	private float minZoom;
	@SuppressWarnings("unused")
	private float height;
	@SuppressWarnings("unused")
	private float width;
	@SuppressWarnings("unused")
	private RectF viewRect;

	private ImageView foto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imagem_membro);
		
		foto = (ImageView) findViewById(R.id.imagemMembro);
		
		Intent intent = getIntent();
		String fotoByte = intent.getStringExtra(getString(R.string.param_foto_membro));
		
		if (fotoByte == null){
			
			AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
			fadeIn.setDuration(700);
			fadeIn.setFillAfter(true);
			
			foto.startAnimation(fadeIn);
			foto.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
							
		} else {
							
			byte[] decode = Base64.decode(fotoByte, Base64.DEFAULT);
			Bitmap bm = BitmapFactory.decodeByteArray(decode, 0, decode.length);
			Options op = new BitmapFactory.Options();
			op.inScaled = false;
			Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bm, 1000, 1000, true);
							
			AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
			fadeIn.setDuration(700);
			fadeIn.setFillAfter(true);
			
			foto.startAnimation(fadeIn);
			foto.setImageBitmap(createScaledBitmap);
							
		}
		init();
		
		foto.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {

			    switch (event.getAction() & MotionEvent.ACTION_MASK) {
			    case MotionEvent.ACTION_DOWN:
			        savedMatrix.set(matrix);
			        start.set(event.getX(), event.getY());
			        Log.d(TAG, "mode=DRAG");
			        mode = DRAG;
			        break;
			    case MotionEvent.ACTION_POINTER_DOWN:
			        oldDist = spacing(event);
			        Log.d(TAG, "oldDist=" + oldDist);
			        if (oldDist > 10f) {
			            savedMatrix.set(matrix);
			            midPoint(mid, event);
			            mode = ZOOM;
			            Log.d(TAG, "mode=ZOOM");
			        }
			        break;
			    case MotionEvent.ACTION_UP:
			    case MotionEvent.ACTION_POINTER_UP:
			        mode = NONE;
			        Log.d(TAG, "mode=NONE");
			        break;
			    case MotionEvent.ACTION_MOVE:
			        if (mode == DRAW){ onTouchEvent(event);}
			        if (mode == DRAG) {
			                ///code for draging..        
			        } 
			     else if (mode == ZOOM) {
			         float newDist = spacing(event);
			         Log.d(TAG, "newDist=" + newDist);
			         if (newDist > 10f) {
			             matrix.set(savedMatrix);
			             float scale = newDist / oldDist;
			             matrix.getValues(matrixValues);
			             float currentScale = matrixValues[Matrix.MSCALE_X];
			             // limit zoom
			             if (scale * currentScale > maxZoom) {
			                 scale = maxZoom / currentScale; 
			                }else if(scale * currentScale < minZoom){
			                    scale = minZoom / currentScale; 
			                 }
			             matrix.postScale(scale, scale, mid.x, mid.y);
			            }
			     }
			     break;
			    }
			    foto.setImageMatrix(matrix);
			    return true; // indicate event was handled
			}
		});
		
			
	}
		private void init() {
		    maxZoom = 4;
		    minZoom = 0.25f;
		    height = foto.getDrawable().getIntrinsicHeight()+20;
		    width = foto.getDrawable().getIntrinsicWidth()+20;
		    viewRect = new RectF(0, 0, foto.getWidth()+20, foto.getHeight()+20);
		}
		
		private float spacing(MotionEvent event) {
		   float x = event.getX(0) - event.getX(1);
		   float y = event.getY(0) - event.getY(1);
		   return Float.valueOf(String.valueOf(Math.sqrt(x * x + y * y)));
		}
		
		//************* Calculate the mid point of the first two fingers 
		private void midPoint(PointF point, MotionEvent event) {
		   float x = event.getX(0) + event.getX(1);
		   float y = event.getY(0) + event.getY(1);
		   point.set(x / 2, y / 2);
		}
	
}
