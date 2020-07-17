<?php

    $user_id = $_POST['id'];

    $link = mysql_connect("localhost","hozzi","adgjm9654");
	mysql_query("SET NAMES utf8");

    mysql_select_db("hozzi",$link) or die("you choose wrong one");

    $total = mysql_fetch_array(mysql_query("select count(*) from sns_people_info where id ='$user_id'"));
    
    $total = $total["count(*)"];
        

    if($total == 0 ){
        $result = array('result' => ok,'msg' => good);
    }else{
        $result = array('result' => nk,'msg' => notgood);
    }

    echo json_encode($result);

    



    mysql_close($link);

    

?>