<?php
    

$link = mysql_connect("localhost","hozzi","adgjm9654");
mysql_query("SET NAMES utf8");

mysql_select_db("hozzi",$link) or die ("you choose wrong thing");

$memberData = array();


$qry_result = mysql_query("select * from sns_feed_info ORDER BY idx DESC",$link);
while($qry_row = mysql_fetch_array($qry_result)){
    $member = array('board_idx'=>$qry_row['idx'],'author' => $qry_row["author"],'author_idx'=>$qry_row['author_idx'],
        'content'=>$qry_row['content'],'like'=>$qry_row['mylike'],'hash_tag'=>$qry_row['hash_tag']);
    array_push($memberData, $member);
}

$result = array('result'=>ok,'feed_info'=>$memberData);

echo json_encode($result);


    
    
?>
