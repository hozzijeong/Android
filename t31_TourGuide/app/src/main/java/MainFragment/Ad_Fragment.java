package MainFragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.t31_tourguide.R;

public class Ad_Fragment extends Fragment {
    RelativeLayout layout;
    public String color;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.advertisement,container,false);
        layout = v.findViewById(R.id.ad_layout);
        layout.setBackgroundColor(Color.parseColor(color));
        return v;
    }
}
