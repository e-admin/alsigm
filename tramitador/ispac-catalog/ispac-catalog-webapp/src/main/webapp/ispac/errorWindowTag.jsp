<html>
	<head>
		$base
		<title>$title</title>
		<script language='javascript'>
				window.name='errorsWindow';
		</script>
		<link rel='stylesheet' type='text/css' href='css/styles.css'/>        
	</head>
	<body marginwidth='0' marginheight='0'>
		<table cellpadding='0' cellspacing='0' border='0' width='100%' height='100%'>
			<tr>
				<td>
					<table cellpadding='0' cellspacing='1' border='0' width='95%' height='95%' class='box' align='center' valign='middle'>
						<tr>
							<td class='title' height='18px'>
								<table cellpadding='0' cellspacing='0' border='0' width='100%'>
									<tr>
										<td><img src='img/pixel.gif' height='18px'/></td>
										<td width='100%' class='menuhead'>$head</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class='blank'>
								<table cellpadding='2' cellspacing='0' border='0' width='100%'>
									<tr>
						 				<td class='menu' align='center' height='50px' valign='top'>
						 					$errors
						 				</td>					
									</tr>
									<tr>
						 				<td align='center'>
									  	<a href='javascript:window.close();' class='schema'>$back</a>
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
