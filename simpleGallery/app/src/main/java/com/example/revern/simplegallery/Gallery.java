package com.example.revern.simplegallery;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

public class Gallery extends AppCompatActivity implements AdapterView.OnItemClickListener{
    GridView gridView;
    ArrayList<File> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(Build.VERSION.SDK_INT>=21){
            TransitionInflater inflater = TransitionInflater.from(this);
            Transition transition = inflater.inflateTransition(R.transition.transition_a);
            getWindow().setExitTransition(transition);Slide slide = new Slide();
            slide.setDuration(1000);
            getWindow().setEnterTransition(slide);
            getWindow().setSharedElementExitTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_element_transition));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        list = imageReader(Environment.getExternalStorageDirectory());


        gridView= (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new GridAdapter());
        gridView.setOnItemClickListener(this);

    }
    ArrayList<File> imageReader(File root){
        ArrayList<File> a = new ArrayList<>();
        File[] files = root.listFiles();
        try {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    a.addAll(imageReader(files[i]));
                } else {
                    if (files[i].getName().endsWith(".jpg") || files[i].getName().endsWith(".jpeg") || files[i].getName().endsWith(".png") || files[i].getName().endsWith(".gif")) {
                        a.add(files[i]);
                    }
                }

            }
        }catch (Exception e){

        }
        return a;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        view.setTransitionName("selectedPic");
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, view.getTransitionName());
        Intent intent = new Intent(Gallery.this, FullPictureActivity.class);
        intent.putExtra(FullPictureActivity.EXTRA_PICTURE, list.get(position).toString());
        startActivity(intent, options.toBundle());
    }

    public class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView=getLayoutInflater().inflate(R.layout.single_grid, parent,false);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
            imageView.setImageURI(Uri.parse(getItem(position).toString()));
            return convertView;
        }

    }
}
