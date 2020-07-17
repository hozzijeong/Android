<?php		
	$post_data=$_POST['eng'];	
	$post_data2=$_POST['kor'];
	
	/*
	$post_data = str_replace('\\', '', $post_data);
	$post_data = json_decode($post_data);
	$key = $post_data->{'mb_id'};
	$arr = $post_data->{'arr'};   
	*/

	$json_arr = array('result'=>OK,'eng'=>$post_data,'kor'=>$post_data2);		

	echo json_encode($json_arr);
/*
	$link = mysql_connect("localhost","아이디","비밀번호");
	mysql_query("SET NAMES utf8");
*/
/*
	if($link){
		echo "연결성공:   "; 
	}else{
		echo "연결실패";
	}
*/
//	mysql_select_db("gvc",$link) or die("you choose wrong one");

	//데이터 갯수 가져오기
	/*
	$total=mysql_fetch_array(mysql_query("select count(*) from gvc_notice"));
	$total = $total["count(*)"];
	echo $total."\n";
	*/
	
	// 데이터 전부 불러오기	
	/*
	$qry_result =  mysql_query("select * from gvc_notice",$link);	

	$memberDatas = array(); //변수를 배열로 선언

	while($qry_row = mysql_fetch_array($qry_result))
	{
		$member = array("content" => $qry_row["content"] , "date" => $qry_row["date"]); 
		array_push($memberDatas, $member);
	}

	mysql_close($link);
	*/
	
	
	//역순으로 불러오기
	/*
	$qry_result =  mysql_query("select * from gvc_notice ORDER BY seq DESC",$link);	

	$memberDatas = array();
	while($qry_row = mysql_fetch_array($qry_result))
	{
		$member = array("content" => $qry_row["content"] , "date" => $qry_row["date"]); 
		//echo $qry_row["content"]."<br>";
		array_push($memberDatas, $member);
	}

	mysql_close($link);
	*/

	//특정 갯수만 가져오기
	/*
	$qry_result =  mysql_query("select * from gvc_notice ORDER BY seq DESC LIMIT 3",$link);	

	$memberDatas = array();
	while($qry_row = mysql_fetch_array($qry_result))
	{
		$member = array("content" => $qry_row["content"] , "date" => $qry_row["date"]); 
		array_push($memberDatas, $member);
	}

	mysql_close($link);
*/


	//특정 갯수 특정 글 이후
	/*
	$qry_result =  mysql_query("select * from gvc_notice WHERE seq<'14' ORDER BY seq DESC LIMIT 3",$link);	

	$memberDatas = array();
	while($qry_row = mysql_fetch_array($qry_result))
	{
		$member = array("content" => $qry_row["content"] , "date" => $qry_row["date"]); 
		array_push($memberDatas, $member);
	}

	mysql_close($link);
	*/

	//특정 글 수정하기
	/*	
	$qry_result =  mysql_query("update gvc_notice set content='강아지 개굴개굴' WHERE seq='10'",$link);		

	mysql_close($link);
	


	$result = array('result'=>OK,'list'=>$memberDatas);		
	*/			
//	echo json_encode($result);

?>