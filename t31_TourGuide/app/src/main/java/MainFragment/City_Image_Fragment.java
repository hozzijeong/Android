package MainFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.t31_tourguide.R;

public class City_Image_Fragment extends Fragment {
    // specific city info 에 있는 viewpager 에들어갈 이미지들을 설정하는 imageFragment

    ImageView imageView;
    public String url;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.city_pictures,container,false);
        imageView = v.findViewById(R.id.city_image);
        Glide.with(this).load(url).into(imageView);

        return v;
    }
}
