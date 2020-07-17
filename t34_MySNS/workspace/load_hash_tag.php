<?php
    
  
    
    $link = mysql_connect("localhost","hozzi","adgjm9654");
    mysql_query("SET NAMES utf8");
    
    mysql_select_db("hozzi",$link) or die ("you choose wrong thing");
    
    $memberData = array();
    
    $qry_result = mysql_query("select * from sns_feed_hash_tag ORDER BY idx DESC",$link);
    while($qry_row = mysql_fetch_array($qry_result)){
        $member = array('board_idx'=>$qry_row['idx'],'content' => $qry_row["content"]);
        array_push($memberData, $member);
    }
    
    $result = array('result'=>ok,'hash_info'=>$memberData);
    
    echo json_encode($result);
    
?>
