<?php

    $user_id = $_POST['id'];
    $user_pw = $_POST['pw'];
    
    $link = mysql_connect("localhost","hozzi","adgjm9654");
    mysql_query("SET_NAME_utf8");
    mysql_select_db("hozzi",$link) or die("you choose wrong one");

   

    $total = mysql_fetch_array(mysql_query("select count(*) from sns_people_info where id = '$user_id' and pw = '$user_pw'"));

    $total = $total["count(*)"];
    

    $result = null;
    $memberData = array();

    if($total != 1){            
        $result = array('result'=>nk,'msg'=>"check your info",'total'=>$total);
    }else{
        echo "id: ".$user_id."<br>pw: ".$user_pw;
        $qry_result = mysql_query("select * from sns_people_info where id = '$user_id' and pw ='$user_pw'",$link);
        while($qry_row = mysql_fetch_array($qry_result)){
            $member = array(
                'name'=> $qry_row['name'],
                'nick' =>$qry_row['nick'],
                'profile'=>$qry_row['url'],
                'idx'=>$qry_row['idx']);    

            array_push($memberData,$member);
        }
        $result = array('result'=>ok,'info'=>$memberData);
    }
        
    echo json_encode ($result);

    mysql_close($link);

?>