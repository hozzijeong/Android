package com.example.t39_tablayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ViewPager_Adapter extends FragmentStatePagerAdapter {

    private int pageCount;
    public ViewPager_Adapter(@NonNull FragmentManager fm,int pageCount) {
        super(fm);

        this.pageCount= pageCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Main_Fragment main_fragment = new Main_Fragment();
                return main_fragment;
            case 1:
                Game_Fragment game_fragment = new Game_Fragment();
                return game_fragment;
            case 2:
                Music_Fragment music_fragment = new Music_Fragment();
                return music_fragment;
            case 3:
                Video_Fragment video_fragment = new Video_Fragment();
                return video_fragment;
            case 4:
                Book_Fragment book_fragment = new Book_Fragment();
                return book_fragment;

            default: return null;
        }
    }

    @Override
    public int getCount() {
        return (pageCount!=0)?pageCount:0;
    }
}

