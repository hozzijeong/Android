package Helper;

import java.util.ArrayList;

public class Voca {
    public String eng;
    public String kor;
    public int idx;

      public Voca(String eng,String kor){
          this.eng = eng;
          this.kor = kor;
      }

      public Voca(int idx,String eng, String kor) {
          this.idx = idx;
          this.eng = eng;
          this.kor = kor;
      }

}
