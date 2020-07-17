package AppHelper;


import java.util.ArrayList;
import java.util.Arrays;

public class Main_Feed_Info {
    public String author_name,content,author_iv_url,hash_tag;
    public boolean like;
    public int board_idx,author_idx;
    public ArrayList<String> hash = new ArrayList<>();
    public boolean check =false;

    public Main_Feed_Info(int board_idx,String author_name,int author_idx,String content){
        this.board_idx = board_idx;
        this.author_name = author_name;
        this.author_idx = author_idx;
        this.content = content;
        this.hash_tag = hash_tag;
        setHash_tag();
    }

    public Main_Feed_Info(boolean like){
        this.like = like;
    }

    /*
        1.해시태그에서 board_idx와 태그된 내용을 idx -> content 형태로 받아옴
        2. Main_Feed_Info 에서 해당 board_idx와 해시테그에 맞는 idx를 구분함.
        3. 서로 값이 같다면, 해당 board_idx에 content를 추가해는 생성자 실행.
        4. hash(arrayList)에 데이터 추가되는 방식으로 진행
     */

    public Main_Feed_Info(String hash_tag){
        hash.add(hash_tag);
    }

    private void setHash_tag(){
        if(hash_tag.length() != 0){
            String[] temp = hash_tag.split(",");
            hash.addAll(Arrays.asList(temp));
        }
    }
}
