package AppHelper;

public class Main_Board_Comment {
    /*
        댓글들의 정보를 담는 클래스.
     */
    public int board_idx;
    public String author,comment,url;

    public Main_Board_Comment(int board_idx,String author,String comment,String url){
        this.board_idx = board_idx;
        this.author = author;
        this.comment = comment;
        this.url = url;

    }
}
