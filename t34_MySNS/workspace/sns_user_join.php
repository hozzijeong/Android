<?php

    $user_id = $_POST['id'];
    $user_pw = $_POST['pw'];
    $user_name = $_POST['name'];
    $user_email = $_POST['mail'];
    $user_nick = $_POST['nick'];
    $user_gender = $_POST['gender'];
    $user_profile = $_POST['profile_url'];

    

    $link = mysql_connect("localhost","hozzi","adgjm9654");
    mysql_query("SET NAMES utf8");

    mysql_select_db("hozzi",$link) or die("you choose wrong one");

   
    mysql_query("insert into sns_people_info(id,pw,name,mail,nick,gender,url) 
        values('$user_id','$user_pw','$user_name','$user_email','$user_nick','$user_gender','$user_profile')",$link);

    $result = array('result' => ok, 'msg' => good);

    echo json_encode($result);

    mysql_close($link);

?>