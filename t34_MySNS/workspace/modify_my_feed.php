<?php
      
    $board_idx = $_POST['board_idx'];
    $content = $_POST['content'];
    $hash_tag = $_POST['hash_tag'];

    $link = mysql_connect("localhost","hozzi","adgjm9654");
    mysql_query("SET NAMES utf8");
    
    mysql_select_db("hozzi",$link) or die ("you choose wrong thing");
    
    
    $qry_result = mysql_query("update sns_feed_info
        set content ='$content',
            hash_tag='$hash_tag'
        where idx ='$board_idx'",$link);
    
    $result = array('result'=>ok);
   
    echo json_encode($result);
    
    
?>
