<?php

    $author = $_POST['author'];
    $author_idx = $_POST['author_idx'];
    $content = $_POST['content'];
    $like = $_POST['like'];
    $hash = $_POST['hash_tag'];
    
    echo $author;
    echo $content;
    echo $like;

    
    $link = mysql_connect("localhost","hozzi","adgjm9654");
    
    mysql_select_db("hozzi",$link) or die("you choose wrong thing");
    
    mysql_query("SET NAMES utf8");
    
    mysql_query("insert into sns_feed_info(author,author_idx,content,mylike,hash_tag)
        values('$author','$author_idx','$content','$like','$hash')");
        
    $result = array('result' => ok,'msg'=>$author);
    
    echo json_encode($result);
    
    mysql_close($link);

?>