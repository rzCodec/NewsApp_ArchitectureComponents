package myapp.newsapp_architecturecomponents.ui;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.*;

public class ImageHandler {
	/**
	 * Context is the context of the activity
	 * Bitmap is the bitmap data of the image
	 * The filename as a string in .png or other image extensions.
	 * Returns a string which is the file path to the image which is stored on the device. A file path is like the address of an image.
	*/
    public String saveToInternalStorage(Bitmap bitmapImage, Context context, String filename) {
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, filename);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public Bitmap loadImageFromStorage(String path, String filename) {
        Bitmap b = null;
        FileInputStream fis = null;
        try {
            File file = new File(path, filename);
            fis = new FileInputStream(file);
            b = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return b;
    }

    /*
    public void saveImageFile(Context context, Bitmap bitmap, String fileName){
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        }
        catch (FileNotFoundException e) {
            Log.d(TAG, "file not found");
            e.printStackTrace();
        }
        catch (IOException e) {
            Log.d(TAG, "io exception");
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            }
            catch (Exception ex) {

            }
        }
    }

    public Bitmap loadBitmap(Context context, String fileName){
        Bitmap bitmap = null;
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(fileName);
            bitmap = BitmapFactory.decodeStream(fis);
        }
        catch (FileNotFoundException e) {
            Log.d(TAG, "file not found");
            e.printStackTrace();
        }
        catch (IOException e) {
            Log.d(TAG, "io exception");
            e.printStackTrace();
        } finally {

            try {
                fis.close();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return bitmap;
    }*/

}
