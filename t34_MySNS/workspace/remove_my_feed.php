<?php
      
    $board_idx = $_POST['board_idx'];
   

    $link = mysql_connect("localhost","hozzi","adgjm9654");
    mysql_query("SET NAMES utf8");
    
    mysql_select_db("hozzi",$link) or die ("you choose wrong thing");
    
    $qry_result = mysql_query("DELETE FROM sns_feed_info where idx ='$board_idx'",$link);
   
    if($qry_result){
        $result = array('result'=>ok);        
    }else{
        $result = array('result'=>nk);
    }
   
    echo json_encode($result);
    
    
?>
