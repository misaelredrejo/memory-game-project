package com.misael_redrejo.memorymisaelredrejo.utils;

import com.misael_redrejo.memorymisaelredrejo.R;
import com.misael_redrejo.memorymisaelredrejo.models.ImageMemory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Util {

    public static ArrayList<ImageMemory> getImageListEasy() {
        ArrayList<ImageMemory> imageListEasy = new ArrayList<ImageMemory>() {{
            add(new ImageMemory(R.drawable.image01));
            add(new ImageMemory(R.drawable.image02));
            add(new ImageMemory(R.drawable.image03));
            add(new ImageMemory(R.drawable.image01));
            add(new ImageMemory(R.drawable.image02));
            add(new ImageMemory(R.drawable.image03));
        }};
        Collections.shuffle(imageListEasy);
        return  imageListEasy;
    }

    public static ArrayList<ImageMemory> getImageListHard() {
        ArrayList<ImageMemory> imageListHard = new ArrayList<ImageMemory>() {{
            add(new ImageMemory(R.drawable.image01));
            add(new ImageMemory(R.drawable.image02));
            add(new ImageMemory(R.drawable.image03));
            add(new ImageMemory(R.drawable.image01));
            add(new ImageMemory(R.drawable.image02));
            add(new ImageMemory(R.drawable.image03));
            add(new ImageMemory(R.drawable.image04));
            add(new ImageMemory(R.drawable.image05));
            add(new ImageMemory(R.drawable.image06));
            add(new ImageMemory(R.drawable.image07));
            add(new ImageMemory(R.drawable.image08));
            add(new ImageMemory(R.drawable.image04));
            add(new ImageMemory(R.drawable.image05));
            add(new ImageMemory(R.drawable.image06));
            add(new ImageMemory(R.drawable.image07));
            add(new ImageMemory(R.drawable.image08));
        }};
        Collections.shuffle(imageListHard);
        return  imageListHard;
    }

}
