package com.example.revern.simplegallery;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBarActivity;
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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Gallery extends ActionBarActivity implements AdapterView.OnItemClickListener {
    private GridView mGridView;
    private List<String> links;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= 21) {
            TransitionInflater inflater = TransitionInflater.from(this);
            Transition transition = inflater.inflateTransition(R.transition.transition_a);
            getWindow().setExitTransition(transition);
            Slide slide = new Slide();
            slide.setDuration(1000);
            getWindow().setEnterTransition(slide);
            getWindow().setSharedElementExitTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_element_transition));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        links = getImagesUrls();

        mGridView = (GridView) findViewById(R.id.gridView);
        mGridView.setAdapter(new GridAdapter());
        mGridView.setOnItemClickListener(this);

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
        intent.putExtra(FullPictureActivity.EXTRA_PICTURE, links.get(position).toString());
        startActivity(intent, options.toBundle());
    }

    public class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return links.size();
        }

        @Override
        public Object getItem(int position) {
            return links.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.single_grid, parent, false);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
            Picasso.with(getApplicationContext()).load(links.get(position)).placeholder(R.drawable.progress_animation).into(imageView);
            return convertView;
        }
    }

    @NonNull
    public static List<String> getImagesUrls() {
        return new ArrayList<String>() {
            {
                add("http://marketingland.com/wp-content/ml-loads/2012/01/android_logo-1.gif");
                add("http://samsung-galaxy3.com/wp-content/uploads/2013/01/9368_132941336415.jpg");
                add("http://www.google.ru/intl/ru/mobile/android/images/android.jpg");
                add("http://mobinfo.kz/wp-content/uploads/2014/06/android_logo.png");
                add("http://www.siliconrus.com/wp-content/uploads/2014/02/android-640x480-wallpaper-889.jpg");
                add("http://www.computerra.ru/wp-content/uploads/2013/08/Android-Army-1.jpg");
                add("http://www.iphones.ru/wp-content/uploads/2012/02/01-2-Android5.jpg");
                add("http://www-static.se-mc.com/blogs.dir/0/files/2015/03/sonymobile-xperia-software-major-01-header-4000px-66013a147af741bcc35ed2fe6231a1e2-940.jpg");
                add("http://www.3dnews.ru/assets/external/illustrations/2012/12/26/639591/androidwallpaper.jpg");
                add("http://picpulp.com/wp-content/uploads/2013/03/android_wallpaper_by_kubines-d48mixu.jpg");
                add("http://www.stroydodyr.ru/files/pervaya1.jpg");
                add("http://www.1366x768.ru/illusion/7/illusion-wallpaper-1366x768.jpg");
                add("http://img12.nnm.me/e/a/4/1/5/ea415692686786b290e95cf8e86f5c02_full.jpg");
                add("http://www.1366x768.ru/illusion/16/illusion-wallpaper-1366x768.jpg");
                add("http://img1.liveinternet.ru/images/attach/c/0//63/240/63240531_onihitode2.jpg");
                add("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcSZNNVk1BU4MyH8JdixzjXa52MEGXZADapc04TXJdmqWsEj1snJ6w");
                add("http://img0.joyreactor.cc/pics/post/%25D0%25BE%25D0%25BF%25D1%2582%25D0%25B8%25D1%2587%25D0%25B5%25D1%2581%25D0%25BA%25D0%25B8%25D0%25B5-%25D0%25B8%25D0%25BB%25D0%25BB%25D1%258E%25D0%25B7%25D0%25B8%25D0%25B8-%25D0%25BF%25D0%25B5%25D1%2581%25D0%25BE%25D1%2587%25D0%25BD%25D0%25B8%25D1%2586%25D0%25B0-496576.jpeg");
                add("https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQbf1-CXP_DpSqG8pvQrOsLv3c96mT6XNkH0A0XyBIkvPfrtMdV");
                add("http://illjuzija.ru/wp-content/uploads/2011/05/illyuziya-vrashhayushhixsya-diskov.jpg");
                add("http://illuzi.ru/files/images/rotbeankakenplastic2.preview.jpg");
                add("http://mcomp.org/wp-content/uploads/2015/03/foto-kosmos-19.jpg");
                add("http://4tololo.ru/files/images/201324101801154389.jpeg");
                add("http://oformi.net/uploads/gallery/main/245/1920-1200-14.jpg");
                add("http://dokkuziklim.net/wp-content/uploads/2014/07/isiq.jpg");
                add("http://statusi3.ru/wp-content/uploads/2013/05/1920-1200-21.jpg");
                add("http://art-print.by/wp-content/uploads/gallery/kosmos/17space10.jpg");
                add("http://www.zastavki.com/pictures/originals/2014/Space_Spiral_space_080593_.jpg");
                add("http://mirgif.com/KARTINKI/kosmos/kosmos-94.jpg");
                add("http://img2.goodfon.ru/original/1920x1080/e/bb/kosmos-galaktika-vselennaya.jpg");
                add("http://unnatural.ru/wp-content/uploads/2014/01/a4.jpeg");
            }
        };
    }
}
