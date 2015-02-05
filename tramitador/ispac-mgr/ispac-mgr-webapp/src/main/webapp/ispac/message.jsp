<html>
	<head>
		$base
		<title>$title</title>
		<script language='javascript'>
				window.name='MessageWindow';
		</script>
		<link rel='stylesheet' type='text/css' href='css/styles.css'/>        
	</head>

	<body marginwidth='0' marginheight='0'>

	<table cellpadding='0' cellspacing='1' border='0' class='boxGris' width='96%' style='margin:8px'>
	<tr>
		<td width='100%'>
			<table cellpadding='0' cellspacing='0' border='0' width='100%'>
				<tr>
					<td width='100%' style='text-align:right'>
						<table border='0' cellspacing='0' cellpadding='0' width='100%'>
							<tr>
								<td style='text-align:right'>
									<input type='button' value='$close' class='form_button_white' onclick='javascript:window.close();'/>
								</td>
								<td width='5px'>
									&nbsp;
								</td>
							</tr>
						</table>						
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td width='100%' class='blank'>
			<table border='0' cellspacing='2' cellpadding='2' width='100%'>
				<tr>
					<td width='100%'>
						<table cellpadding='6' cellspacing='0' border='0' width='100%'>
							<tr>
						 		<td align='center' class='titleForm'>
						 			$message
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	</table>


	</body>
</html>
