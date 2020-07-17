<?php
      
    $search_data = $_REQUEST['search_data'];

    require_once '../sns/Mysql.php';
    $link = db_connect();
    
    
    try{
        $query = "select * from sns_member_info where nick = '$search_data'";
        $stmh = $link->query($query);
        $count = $stmh->rowCount();
        
        if($count == 0){
            $result = array('result'=>"nk",'msg'=>"등록된 게시글이 없습니다.");
            echo json_encode($result);
        }else{
            $row = $stmh->fetch(PDO::FETCH_ASSOC);
            $name = $row["name"];
            $nick = $row["nick"];
            $url = $row["url"];
            $idx = $row["idx"];
            $result = array('result'=>'ok','name'=>$name,'nick'=>$nick,'url'=>$url,'idx'=>$idx);
            echo json_encode($result);
           
        }
    }catch (Exception $exc){
        echo $exc ->getTraceAsString();
    }
       
    
?>
