<?php
    
    $board_idx = $_POST['board_idx'];

    $link = mysql_connect("localhost","hozzi","adgjm9654");
    mysql_query("SET NAMES utf8");
    
    mysql_select_db("hozzi",$link) or die ("you choose wrong thing");
    
    
    $total = mysql_fetch_array(mysql_query(
        "select count(*) from sns_comment_info where board_idx ='$board_idx'"));
    
    $total = $total["count(*)"];
    
    $memberData = array();
    
    $result = null;
    
    if($total == 0){
        $result = array('result'=>nk,'total'=>$total);
    }else{
        $qry_result = mysql_query("select * from sns_comment_info where board_idx = '$board_idx'",$link);
        while($qry_row = mysql_fetch_array($qry_result)){
            $member = array('board_idx'=>$qry_row['board_idx'],'author' => $qry_row["author"],
                'content'=>$qry_row['content'],'profile' =>$qry_row['profile']);
            array_push($memberData, $member);
        }        
        $result = array('result'=>ok,'comment_info'=>$memberData);
    }
    
    echo json_encode($result);
    
    
?>