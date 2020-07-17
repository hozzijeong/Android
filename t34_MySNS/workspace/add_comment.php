<?php

    $author = $_POST['author'];
    $content = $_POST['content'];
    $board_idx = $_POST['board_idx'];
    $profile_url = $_POST['url'];
    
    
    $link = mysql_connect("localhost","hozzi","adgjm9654");
    
    mysql_select_db("hozzi",$link) or die("you choose wrong thing");
    
    mysql_query("SET NAMES utf8");
    
    mysql_query("insert into sns_comment_info(author,content,board_idx,profile)
        values('$author','$content','$board_idx','$profile_url')");
    
    $result = array('result' => ok,'msg'=>$author);
    
    echo json_encode($result);
    
    mysql_close($link);

?>