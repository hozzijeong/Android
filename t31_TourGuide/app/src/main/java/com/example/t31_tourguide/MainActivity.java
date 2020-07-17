package com.example.t31_tourguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import Helper.Advertisement;
import Helper.Place_Info;
import MainFragment.City_List_Fragment;
import MainFragment.Search_Fragment;
import MainFragment.Specific_City_Info_Fragment;
import MainFragment.Spot_Info_Fragment;
import MainFragment.Tour_Spot_List_Fragment;
import MainFragment.Tour_Spot_Preview_Fragment;

/*
    해야할 일
    temp 만들어서 즐겨찾기/ 검색창 활성화 하기
    temp 임시변수 를 통해 저장하고, listView에 temp를 넣음으로써 데이터 저장과 새로운 데이터를 보여주는
    임시 배열 만들기
    따로 데이터를 저장하는 알림장이 있어야 하는데, 그게 없으니까 데이터가 계속해서 초기화가 됨.
    기존의 데이터를 처음부터 저장 할 수 있는 배열을 따로 한개 만들어 놔야 함(sqlite 사용 해볼 것)

    6. 전체적으로 어색한 것들 다듬기.
 */


public class MainActivity extends AppCompatActivity {
    FragmentManager fm;
    FragmentTransaction ft;
    City_List_Fragment city;
    Specific_City_Info_Fragment city_info;
    Search_Fragment search_fragment;
    Tour_Spot_Preview_Fragment preview;
    Tour_Spot_List_Fragment list;
    Spot_Info_Fragment spot_info;
    ArrayList<String> menu = new ArrayList<>();
    RelativeLayout second, main;
    GoogleMap map;

    public DrawerLayout drawer;
    public static ArrayList<Place_Info> infos = new ArrayList<>();
    public static ArrayList<Place_Info> temp = new ArrayList<>();
    public boolean search_condition = false;
    public String search_continent = "";
    public ListView menu_lv;
    public static int pos;
    public boolean turn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = findViewById(R.id.drawer);
        menu_lv = findViewById(R.id.slider_lv);
        main = findViewById(R.id.main_layout);
        second = findViewById(R.id.second_layout);

        city = new City_List_Fragment(); // 관광지 (도시)들의 리스트를 보여주는 fragment
        search_fragment = new Search_Fragment(); // 검색 버튼을 눌렀을때 list를 보여주는 fragment
        spot_info = new Spot_Info_Fragment();

        init();
        setSpot();
        setMenu();
        temp.addAll(infos);
    }

    public void setMenu() {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, menu);
        menu_lv.setAdapter(adapter);
        menu.add("Home");
        menu.add("즐겨찾기");
        menu.add("사용방법");
        menu.add("설정");
        adapter.notifyDataSetChanged();
    }

    public void init() {
        city = new City_List_Fragment();
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.main_layout, city);
        ft.commit();
    }

    public String[] world_continent = {
            "아시아",
            "유럽",
            "남아메리카",
            "북아메리카",
            "아프리카",
            "오세아니아"
    };

    String[][] asia_cities = {
            {"하노이", "https://media-cdn.tripadvisor.com/media/photo-m/1280/1b/33/f7/12/caption.jpg"},
            {"서울", "https://media-cdn.tripadvisor.com/media/photo-o/06/be/05/80/caption.jpg"},
            {"베이징", "https://media-cdn.tripadvisor.com/media/photo-b/2560x500/14/10/2d/f1/beijing.jpg"},
            {"타이베이", "https://media-cdn.tripadvisor.com/media/photo-m/1280/1b/33/f3/33/caption.jpg"},
            {"도쿄", "https://media-cdn.tripadvisor.com/media/photo-m/1280/1b/4b/5d/10/caption.jpg"},
            {"뭄바이", "https://media-cdn.tripadvisor.com/media/photo-c/2560x500/0b/4e/55/e6/chhatrapati-shivaji-terminus.jpg"},
            {"방콕", "https://media-cdn.tripadvisor.com/media/photo-o/09/1a/2b/83/wat-arun-tempel-der-morgenrote.jpg"},
            {"홍콩", "https://media-cdn.tripadvisor.com/media/photo-c/2560x500/12/f7/4e/97/hong-kong.jpg"},
            {"평양", "https://media-cdn.tripadvisor.com/media/photo-o/0e/b4/8d/b6/rungrado-1st-of-may-stadium.jpg"},
            {"울란바트로 ", "https://media-cdn.tripadvisor.com/media/photo-c/2560x500/04/9a/56/93/scenery.jpg"},
    };

    String[][] europe_cities = {
            {"파리", "https://media-cdn.tripadvisor.com/media/photo-m/1280/1b/4b/59/86/caption.jpg"},
            {"마드리드", "https://media-cdn.tripadvisor.com/media/photo-m/1280/1b/33/e6/bf/caption.jpg"},
            {"로마", "https://media-cdn.tripadvisor.com/media/photo-m/1280/1b/33/cb/56/caption.jpg"},
            {"아테네", "https://media-cdn.tripadvisor.com/media/photo-o/0c/c1/a5/35/photo2jpg.jpg"},
            {"베를린", "https://media-cdn.tripadvisor.com/media/photo-o/1a/90/4d/3f/caption.jpg"},
            {"런던", "https://media-cdn.tripadvisor.com/media/photo-c/2560x500/08/4c/08/1b/sun-peeks-through-behind.jpg"},
            {"스톡홀롬", "https://media-cdn.tripadvisor.com/media/photo-b/2560x500/15/33/f9/5e/stockholm.jpg"},
            {"프라하", "https://media-cdn.tripadvisor.com/media/photo-o/09/0e/67/3a/charles-bridge-karluv.jpg"},
            {"리스본", "https://media-cdn.tripadvisor.com/media/photo-m/1280/1b/4b/5c/a2/caption.jpg"},
            {"암스테르담", "https://media-cdn.tripadvisor.com/media/photo-o/08/10/f8/de/rijksmuseum.jpg"},
            {"모스크바", "https://media-cdn.tripadvisor.com/media/photo-b/2560x500/15/33/fd/62/moscow.jpg"},
    };
    String[][] south_america_cities = {
            {"리우데자네이루", "https://media-cdn.tripadvisor.com/media/photo-o/15/e4/38/0e/fim-de-tarde-hoje-em.jpg"},
            {"부에노스아이레스", "https://media-cdn.tripadvisor.com/media/photo-b/2560x500/15/33/fe/c5/buenos-aires.jpg"},
            {"산티아고", "https://media-cdn.tripadvisor.com/media/photo-c/2560x500/08/7a/58/c7/vista-de-santiago.jpg"},
            {"리마", "https://media-cdn.tripadvisor.com/media/photo-b/2560x500/15/33/fc/bc/lima.jpg"},
            {"보고타", "https://media-cdn.tripadvisor.com/media/photo-o/18/ec/67/ed/cano-cristales-jungle.jpg"},
            {"카라카스", "https://media-cdn.tripadvisor.com/media/photo-b/2560x500/15/33/fe/d4/caracas.jpg"},
            {"몬테비데오", "https://media-cdn.tripadvisor.com/media/photo-o/01/0d/f6/3f/avenida-18-de-julio.jpg"},

    };
    String[][] north_america_cities = {
            {"멕시코시티", "https://media-cdn.tripadvisor.com/media/photo-m/1280/1b/33/f3/96/caption.jpg"},
            {"과테말라", "https://media-cdn.tripadvisor.com/media/photo-o/15/4e/16/c2/20-day-guatemala-belize.jpg"},
            {"파나마", "https://media-cdn.tripadvisor.com/media/photo-o/13/7e/a9/cb/dreams-delight-playa.jpg"},
            {"하바나", "https://media-cdn.tripadvisor.com/media/photo-b/2560x500/15/33/f5/36/havana.jpg"},
            {"워싱턴D.C", "https://media-cdn.tripadvisor.com/media/photo-s/12/3a/ad/70/photo1jpg.jpg"},


    };
    String[][] africa_cities = {
            {"아크라", "https://media-cdn.tripadvisor.com/media/photo-b/2560x500/15/33/fb/47/accra.jpg"},
            {"프리토리아", "https://media-cdn.tripadvisor.com/media/photo-c/2560x500/07/90/75/5d/pic-1.jpg"},
            {"라바트", "https://media-cdn.tripadvisor.com/media/photo-b/2560x500/15/33/fb/40/rabat.jpg"},
            {"예루살렘", "https://media-cdn.tripadvisor.com/media/photo-o/10/24/61/96/western-wall-temple-mount.jpg"},
            {"아디스아바바", "https://media-cdn.tripadvisor.com/media/photo-m/1280/16/b2/89/96/nile-river-tis-esat-falls.jpg"},
            {"카이로", "https://media-cdn.tripadvisor.com/media/photo-c/2560x500/03/9b/2f/5b/cairo.jpg"},

    };
    String[][] oceania_cities = {
            {"캔버라", "https://media-cdn.tripadvisor.com/media/photo-c/2560x500/12/1a/b7/24/balloons-aloft-over-the.jpg"},
            {"호니아라", "https://media-cdn.tripadvisor.com/media/photo-o/0e/fe/82/6b/photo0jpg.jpg"},
            {"수바", "https://media-cdn.tripadvisor.com/media/photo-o/0d/d0/bd/37/20161129085633-largejpg.jpg"},
            {"웰링턴", "https://media-cdn.tripadvisor.com/media/photo-o/01/09/e7/2e/the-cable-car.jpg"},
    };


    // 도시별로 spot_info 데이터 저장은 어떻게 할까?
    /*
        순서는 위와 같다고 해도, 데이터 테이블이 맞아야함.
        city_name 을 추가했음.

     */


    public void setCity() {
        for (String[] asia_city : asia_cities) {
            infos.add(new Place_Info(world_continent[0], asia_city[0], false, 0, asia_city[1]));
        }
        for (String[] europe_city : europe_cities) {
            infos.add(new Place_Info(world_continent[1], europe_city[0], false, 0, europe_city[1]));
        }
        for (String[] south_america_city : south_america_cities) {
            infos.add(new Place_Info(world_continent[2], south_america_city[0], false, 0, south_america_city[1]));
        }
        for (String[] north_america_city : north_america_cities) {
            infos.add(new Place_Info(world_continent[3], north_america_city[0], false, 0, north_america_city[1]));
        }
        for (String[] africa_city : africa_cities) {
            infos.add(new Place_Info(world_continent[4], africa_city[0], false, 0, africa_city[1]));
        }
        for (String[] oceania_city : oceania_cities) {
            infos.add(new Place_Info(world_continent[5], oceania_city[0], false, 0, oceania_city[1]));
        }

    }

    //,"37.5751068","126.9942932"

    String[][] spot = { // 도시명, 명소이름, 설명, url,lat,lng//
            {"하노이", "하노이 구시가", "노상에서먹는 맥주 쵝오 볼거리많고 맛집많고 호안끼엠호수에서 그리멀지안은곳에위치하고있음. 낮과밤의색깔이 너무다른곳 밤에가길추천드림", "https://media-cdn.tripadvisor.com/media/photo-f/13/93/f8/c9/img-20180704-091936-694.jpg", "37.5751068", "126.9942932"},
            {"하노이", "문묘&국립대학교", "유교사원인데 깨끗하고 아름다웠음 하노이에서 제일 가볼만한 곳임 조경을 잘 해놓았음 해마다 유교행사가 지금도 열린다고 함 그리 크지 않아 30분에서 1시간이면 다 볼수 있음 조그마한 동양정원임", "https://media-cdn.tripadvisor.com/media/photo-f/09/10/28/f9/temple-of-literature.jpg", "37.5751068", "126.9942932"},
            {"하노이", "베트남 민족학 박물관", "오랜 전쟁을 겪은 베트남의 현실은 눈부신 경제 성장이지만 이를 뒷받침하는 것은 이것을 있게한 역사이다 특히 호치민의 일화는 매우 감동적이었다", "https://media-cdn.tripadvisor.com/media/photo-f/09/ff/f4/f0/museum-etnologi-vietnam.jpg", "37.5751068", "126.9942932"},
            {"서울", "경복궁", "날씨 좋은 날 종종 산책하러 경복궁에 가는데 마음이 편온해지는 기분이라고 할까요? 특히 봄이나 가을에 산책가는 걸 추천해요!", "https://media-cdn.tripadvisor.com/media/photo-f/15/6a/f8/e8/palace.jpg", "37.5751068", "126.9942932"},
            {"서울", "명동", "Very nice place!! Korea’s famous sandwich franchise!!!", "https://media-cdn.tripadvisor.com/media/photo-f/12/f8/f7/ca/myeongdong-shopping-street.jpg", "37.5751068", "126.9942932"},
            {"서울", "서울 한양 도성", "서울시내를 둘러싸고 있는 서울한양도성. 입구는 여러군데에 위치하고 있습니다. 입구에따라 가벼운코스도있습니다. 야경보기좋습니다.", "https://media-cdn.tripadvisor.com/media/photo-f/0f/6e/60/95/these-are-photos-i-toke.jpg", "37.5751068", "126.9942932"},
            {"타이페이", "장개석 기념관", "중정기념관 정말 웅장하네요.. mrt역에서 내리면 바로 연결됩니다.. 게다가 무료관람~~ 자유광장도 아주 좋았어요.. 계단 한걸음 한걸음이 소중한 경험이네요..", "https://media-cdn.tripadvisor.com/media/photo-i/13/ea/43/8e/nationale-chiang-kai.jpg", "37.5751068", "126.9942932"},
            {"타이페이", "용산사", "주간보다는 해가 지고 불이 켜지면 가는 것이 좋을 것 같아요. 도교와 불교 문화가 합쳐져 이색적이고 매우 화려합니다. 다만 사람들이 너무 많아서 정신이 없었어요", "https://media-cdn.tripadvisor.com/media/photo-f/10/bb/23/0d/photo2jpg.jpg", "37.5751068", "126.9942932"},
            {"타이페이", "대만 국립 고궁 박물관", "대만 타이페이에 위치한 고궁 박물관입니다. 동파육 모양의 돌이 전시되어있습니다. 옥으로 만든 배추모양 장식물이 인기입니다. 물을 가지고 전시장안으로 못들어가니 주의가 필요합니다.", "https://media-cdn.tripadvisor.com/media/photo-i/16/cd/db/0b/photo0jpg.jpg", "37.5751068", "126.9942932"},
            {"도쿄", "센소지", "낮과는 또 다른 매력이 있는 것 같습니다. 낮에는 활력이 넘쳤다면 밤에는 좀 더 조용하고 여운이 남았었습니다. 벤치에 앉아서 야경 바라보며 생각 정리도 하고, 좋네요.", "https://media-cdn.tripadvisor.com/media/photo-f/14/7a/e4/33/sensoji-temple-in-asakusa.jpg", "37.5751068", "126.9942932"},
            {"도쿄", "도쿄역", "도쿄역 역사 주변에 고층 빌딩이 많이 있고 밤 뷰는 이쁜거같다. 주변에 쇼핑몰 음식점도 많이 있고 어디로든 이동하기편하다..", "https://media-cdn.tripadvisor.com/media/photo-f/0a/2e/35/40/photo0jpg.jpg", "37.5751068", "126.9942932"},
            {"도쿄", "모리타워", "일본 기업과 미팅을 하기 위해 방문했는데, 고층에서 바라보는 풍경이 좋습니다. 이전에 힐즈 전망대를 이용했었는데 그것과 다른 느낌이네요", "https://media-cdn.tripadvisor.com/media/photo-i/14/98/bd/2c/caption.jpg", "37.5751068", "126.9942932"},
    };


    // Adapter 연결 할때 spot_Info 가 null인 경우도 생각하기
    public void setSpot() {
        setCity();
        for (int i = 0; i < infos.size(); i++) {
            for (int j = 0; j < spot.length; j++) {
                if (infos.get(i).city.equals(spot[j][0])) {
                    infos.get(i).spot_infos.add(new Place_Info.spot_info(spot[j][0], spot[j][1], spot[j][2], spot[j][3], Float.parseFloat(spot[j][4]), Float.parseFloat(spot[j][5])));
                }
            }
        }
    }

    public int spot_idx = -1;

    public void replaceFragment(int idx) {
        main.setVisibility(View.VISIBLE);
        second.setVisibility(View.INVISIBLE);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        if (idx == -1) {
            pos = -1;
            ft.replace(R.id.main_layout, city);
        } else if (idx == -2) {
            ft.replace(R.id.main_layout, search_fragment);
        } else {
            spot_idx = idx;
            ft.replace(R.id.main_layout, spot_info);
        }
        ft.commit();
    }

    public void showMap(final int idx) {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                LatLng spot = new LatLng(infos.get(pos).spot_infos.get(idx).lat, infos.get(pos).spot_infos.get(idx).lng);

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title(infos.get(pos).spot_infos.get(idx).spot_name);
                map.addMarker(markerOptions);

                map.moveCamera(CameraUpdateFactory.newLatLng(spot));
                map.animateCamera(CameraUpdateFactory.zoomTo(15));
            }
        });
    }


    public void addFragment(int idx, boolean turn) {
        main.setVisibility(View.INVISIBLE);
        second.setVisibility(View.VISIBLE);

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        city_info = new Specific_City_Info_Fragment(); // 도시 상세 정보를 나타내는 fragment
        preview = new Tour_Spot_Preview_Fragment();
        list = new Tour_Spot_List_Fragment();
        pos = idx;

        if (turn) {
            ft.replace(R.id.second_up_layout, city_info);
            ft.replace(R.id.second_down_layout, preview);
        } else {
            ft.replace(R.id.second_up_layout, city_info);
            ft.replace(R.id.second_down_layout, list);
        }

        ft.commit();
    }
}