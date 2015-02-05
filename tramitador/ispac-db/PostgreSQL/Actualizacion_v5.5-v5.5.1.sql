

-----------------------------------
-- Actualización de v5.5 a v5.5.1
-----------------------------------

--
-- Información de versión
--
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONBD', '5.5.1', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONAPP', '5.5.1', current_timestamp);


--
-- Informes
--
ALTER TABLE spac_ct_informes ADD filas smallint;
ALTER TABLE spac_ct_informes ADD columnas smallint;

UPDATE spac_ct_informes SET xml='<?xml version="1.0" encoding="ISO-8859-1"  ?>
<!-- Created with iReport - A designer for JasperReports -->
<!DOCTYPE jasperReport PUBLIC "//JasperReports//DTD Report Design//EN" "http://jasperreports.sourceforge.net/dtds/jasperreport.dtd">
<jasperReport
		 name="EtiquetaExpediente"
		 columnCount="1"
		 printOrder="Vertical"
		 orientation="Portrait"
		 pageWidth="595"
		 pageHeight="842"
		 columnWidth="588"
		 columnSpacing="0"
		 leftMargin="5"
		 rightMargin="2"
		 topMargin="5"
		 bottomMargin="2"
		 whenNoDataType="NoPages"
		 isTitleNewPage="false"
		 isSummaryNewPage="false">
	<property name="ireport.scriptlethandling" value="0" />
	<property name="ireport.encoding" value="UTF-8" />
	<import value="java.util.*" />
	<import value="net.sf.jasperreports.engine.*" />
	<import value="net.sf.jasperreports.engine.data.*" />

	<parameter name="NUM_EXP" isForPrompting="true" class="java.lang.String"/>
	<parameter name="IMAGES_REPOSITORY_PATH" isForPrompting="true" class="java.lang.String"/>
	<parameter name="POSICION" isForPrompting="false" class="java.lang.String"/>
	<queryString><![CDATA[SELECT
     numexp  ,
     asunto ,
     fapertura ,
     utramitadora 
FROM
     spac_expedientes
WHERE
     numexp = $P{NUM_EXP}]]></queryString>

	<field name="numexp" class="java.lang.String"/>
	<field name="asunto" class="java.lang.String"/>
	<field name="fapertura" class="java.sql.Timestamp"/>
	<field name="utramitadora" class="java.lang.String"/>

		<background>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</background>
		<title>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</title>
		<pageHeader>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</pageHeader>
		<columnHeader>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</columnHeader>
		<detail>
			<band height="835"  isSplitAllowed="true" >
				<staticText>
					<reportElement
						x="353"
						y="697"
						width="185"
						height="20"
						key="staticText-25"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("8"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[O. Tramit.]]></text>
				</staticText>
				<staticText>
					<reportElement
						x="353"
						y="677"
						width="185"
						height="20"
						key="staticText-26"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("8"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[F. Inicio]]></text>
				</staticText>
				<staticText>
					<reportElement
						x="353"
						y="484"
						width="185"
						height="20"
						key="staticText-17"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("6"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[O. Tramit.]]></text>
				</staticText>
				<staticText>
					<reportElement
						x="353"
						y="464"
						width="185"
						height="20"
						key="staticText-18"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("6"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[F. Inicio]]></text>
				</staticText>
				<staticText>
					<reportElement
						x="353"
						y="272"
						width="185"
						height="20"
						key="staticText-9"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("4"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[O. Tramit.]]></text>
				</staticText>
				<staticText>
					<reportElement
						x="353"
						y="252"
						width="185"
						height="20"
						key="staticText-10"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("4"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[F. Inicio]]></text>
				</staticText>
				<staticText>
					<reportElement
						x="353"
						y="70"
						width="185"
						height="20"
						key="staticText-1"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("2"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[O. Tramit.]]></text>
				</staticText>
				<staticText>
					<reportElement
						x="353"
						y="50"
						width="185"
						height="20"
						key="staticText-2"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("2"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[F. Inicio]]></text>
				</staticText>
				<staticText>
					<reportElement
						x="54"
						y="10"
						width="65"
						height="20"
						key="staticText">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("1"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[Num. Exp]]></text>
				</staticText>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="119"
						y="10"
						width="120"
						height="20"
						key="textField">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("1"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{numexp}]]></textFieldExpression>
				</textField>
				<staticText>
					<reportElement
						x="54"
						y="30"
						width="65"
						height="20"
						key="staticText">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("1"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[Desc.]]></text>
				</staticText>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="119"
						y="30"
						width="120"
						height="20"
						key="textField">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("1"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000" bottomPadding="5"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{asunto}]]></textFieldExpression>
				</textField>
				<staticText>
					<reportElement
						x="54"
						y="50"
						width="185"
						height="20"
						key="staticText"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("1"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[F. Inicio]]></text>
				</staticText>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="119"
						y="50"
						width="120"
						height="20"
						key="textField"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("1"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[ieci.tdw.ispac.ispaclib.utils.DateUtil.format($F{fapertura})]]></textFieldExpression>
				</textField>
				<staticText>
					<reportElement
						x="54"
						y="70"
						width="185"
						height="20"
						key="staticText"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("1"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[O. Tramit.]]></text>
				</staticText>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="119"
						y="70"
						width="120"
						height="20"
						key="textField"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("1"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{utramitadora}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="418"
						y="10"
						width="120"
						height="20"
						key="textField-1">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("2"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{numexp}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="418"
						y="30"
						width="120"
						height="20"
						key="textField-2">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("2"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000" bottomPadding="5"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{asunto}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="418"
						y="50"
						width="120"
						height="20"
						key="textField-3"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("2"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[ieci.tdw.ispac.ispaclib.utils.DateUtil.format($F{fapertura})]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="418"
						y="70"
						width="120"
						height="20"
						key="textField-4"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("2"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{utramitadora}]]></textFieldExpression>
				</textField>
				<staticText>
					<reportElement
						x="353"
						y="30"
						width="65"
						height="20"
						key="staticText-3">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("2"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[Desc.]]></text>
				</staticText>
				<staticText>
					<reportElement
						x="353"
						y="10"
						width="65"
						height="20"
						key="staticText-4">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("2"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[Num. Exp]]></text>
				</staticText>
				<staticText>
					<reportElement
						x="54"
						y="212"
						width="65"
						height="20"
						key="staticText-5">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("3"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[Num. Exp]]></text>
				</staticText>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="119"
						y="212"
						width="120"
						height="20"
						key="textField-5">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("3"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{numexp}]]></textFieldExpression>
				</textField>
				<staticText>
					<reportElement
						x="54"
						y="232"
						width="65"
						height="20"
						key="staticText-6">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("3"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[Desc.]]></text>
				</staticText>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="119"
						y="232"
						width="120"
						height="20"
						key="textField-6">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("3"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000" bottomPadding="5"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{asunto}]]></textFieldExpression>
				</textField>
				<staticText>
					<reportElement
						x="54"
						y="252"
						width="185"
						height="20"
						key="staticText-7"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("3"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[F. Inicio]]></text>
				</staticText>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="119"
						y="252"
						width="120"
						height="20"
						key="textField-7"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("3"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[ieci.tdw.ispac.ispaclib.utils.DateUtil.format($F{fapertura})]]></textFieldExpression>
				</textField>
				<staticText>
					<reportElement
						x="54"
						y="272"
						width="185"
						height="20"
						key="staticText-8"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("3"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[O. Tramit.]]></text>
				</staticText>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="119"
						y="272"
						width="120"
						height="20"
						key="textField-8"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("3"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{utramitadora}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="418"
						y="212"
						width="120"
						height="20"
						key="textField-9">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("4"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{numexp}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="418"
						y="232"
						width="120"
						height="20"
						key="textField-10">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("4"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000" bottomPadding="5"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{asunto}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="418"
						y="252"
						width="120"
						height="20"
						key="textField-11"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("4"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[ieci.tdw.ispac.ispaclib.utils.DateUtil.format($F{fapertura})]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="418"
						y="272"
						width="120"
						height="20"
						key="textField-12"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("4"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{utramitadora}]]></textFieldExpression>
				</textField>
				<staticText>
					<reportElement
						x="353"
						y="232"
						width="65"
						height="20"
						key="staticText-11">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("4"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[Desc.]]></text>
				</staticText>
				<staticText>
					<reportElement
						x="353"
						y="212"
						width="65"
						height="20"
						key="staticText-12">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("4"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[Num. Exp]]></text>
				</staticText>
				<staticText>
					<reportElement
						x="54"
						y="424"
						width="65"
						height="20"
						key="staticText-13">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("5"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[Num. Exp]]></text>
				</staticText>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="119"
						y="424"
						width="120"
						height="20"
						key="textField-13">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("5"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{numexp}]]></textFieldExpression>
				</textField>
				<staticText>
					<reportElement
						x="54"
						y="444"
						width="65"
						height="20"
						key="staticText-14">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("5"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[Desc.]]></text>
				</staticText>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="119"
						y="444"
						width="120"
						height="20"
						key="textField-14">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("5"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000" bottomPadding="5"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{asunto}]]></textFieldExpression>
				</textField>
				<staticText>
					<reportElement
						x="54"
						y="464"
						width="185"
						height="20"
						key="staticText-15"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("5"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[F. Inicio]]></text>
				</staticText>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="119"
						y="464"
						width="120"
						height="20"
						key="textField-15"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("5"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[ieci.tdw.ispac.ispaclib.utils.DateUtil.format($F{fapertura})]]></textFieldExpression>
				</textField>
				<staticText>
					<reportElement
						x="54"
						y="484"
						width="185"
						height="20"
						key="staticText-16"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("5"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[O. Tramit.]]></text>
				</staticText>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="119"
						y="484"
						width="120"
						height="20"
						key="textField-16"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("5"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{utramitadora}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="418"
						y="424"
						width="120"
						height="20"
						key="textField-17">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("6"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{numexp}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="418"
						y="444"
						width="120"
						height="20"
						key="textField-18">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("6"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000" bottomPadding="5"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{asunto}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="418"
						y="464"
						width="120"
						height="20"
						key="textField-19"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("6"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[ieci.tdw.ispac.ispaclib.utils.DateUtil.format($F{fapertura})]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="418"
						y="484"
						width="120"
						height="20"
						key="textField-20"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("6"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{utramitadora}]]></textFieldExpression>
				</textField>
				<staticText>
					<reportElement
						x="353"
						y="444"
						width="65"
						height="20"
						key="staticText-19">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("6"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[Desc.]]></text>
				</staticText>
				<staticText>
					<reportElement
						x="353"
						y="424"
						width="65"
						height="20"
						key="staticText-20">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("6"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[Num. Exp]]></text>
				</staticText>
				<staticText>
					<reportElement
						x="54"
						y="637"
						width="65"
						height="20"
						key="staticText-21">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("7"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[Num. Exp]]></text>
				</staticText>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="119"
						y="637"
						width="120"
						height="20"
						key="textField-21">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("7"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{numexp}]]></textFieldExpression>
				</textField>
				<staticText>
					<reportElement
						x="54"
						y="657"
						width="65"
						height="20"
						key="staticText-22">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("7"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[Desc.]]></text>
				</staticText>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="119"
						y="657"
						width="120"
						height="20"
						key="textField-22">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("7"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{asunto}]]></textFieldExpression>
				</textField>
				<staticText>
					<reportElement
						x="54"
						y="677"
						width="185"
						height="20"
						key="staticText-23"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("7"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[F. Inicio]]></text>
				</staticText>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="119"
						y="677"
						width="120"
						height="20"
						key="textField-23"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("7"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[ieci.tdw.ispac.ispaclib.utils.DateUtil.format($F{fapertura})]]></textFieldExpression>
				</textField>
				<staticText>
					<reportElement
						x="54"
						y="697"
						width="185"
						height="20"
						key="staticText-24"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("7"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[O. Tramit.]]></text>
				</staticText>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="119"
						y="697"
						width="120"
						height="20"
						key="textField-24"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("7"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{utramitadora}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="418"
						y="637"
						width="120"
						height="20"
						key="textField-25">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("8"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{numexp}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="418"
						y="657"
						width="120"
						height="20"
						key="textField-26">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("8"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{asunto}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="418"
						y="677"
						width="120"
						height="20"
						key="textField-27"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("8"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[ieci.tdw.ispac.ispaclib.utils.DateUtil.format($F{fapertura})]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="418"
						y="697"
						width="120"
						height="20"
						key="textField-28"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("8"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{utramitadora}]]></textFieldExpression>
				</textField>
				<staticText>
					<reportElement
						x="353"
						y="657"
						width="65"
						height="20"
						key="staticText-27">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("8"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[Desc.]]></text>
				</staticText>
				<staticText>
					<reportElement
						x="353"
						y="637"
						width="65"
						height="20"
						key="staticText-28">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("8"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[Num. Exp]]></text>
				</staticText>
			</band>
		</detail>
		<columnFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</columnFooter>
		<pageFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</pageFooter>
		<summary>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</summary>
</jasperReport>',
fecha = current_timestamp,
descripcion = 'Informe Etiquetas 4x2',
filas = 4,
columnas = 2
where id=1;

UPDATE spac_ct_informes SET xml='<?xml version="1.0" encoding="ISO-8859-1"  ?>
<!-- Created with iReport - A designer for JasperReports -->
<!DOCTYPE jasperReport PUBLIC "//JasperReports//DTD Report Design//EN" "http://jasperreports.sourceforge.net/dtds/jasperreport.dtd">
<jasperReport
		 name="FichaExpediente"
		 columnCount="1"
		 printOrder="Vertical"
		 orientation="Landscape"
		 pageWidth="842"
		 pageHeight="595"
		 columnWidth="535"
		 columnSpacing="0"
		 leftMargin="20"
		 rightMargin="20"
		 topMargin="20"
		 bottomMargin="20"
		 whenNoDataType="NoPages"
		 isTitleNewPage="false"
		 isSummaryNewPage="false">
	<property name="ireport.scriptlethandling" value="0" />
	<property name="ireport.encoding" value="ISO-8859-1" />
	<import value="java.util.*" />
	<import value="net.sf.jasperreports.engine.*" />
	<import value="net.sf.jasperreports.engine.data.*" />

	<parameter name="NUM_EXP" isForPrompting="true" class="java.lang.String"/>
	<parameter name="IMAGES_REPOSITORY_PATH" isForPrompting="true" class="java.lang.String"/>
	<queryString><![CDATA[SELECT
     spac_expedientes.numexp,
     spac_expedientes.asunto,
     spac_expedientes.poblacion,
     spac_expedientes.localizacion,
     spac_expedientes.municipio,
     spac_expedientes.fapertura,
     spac_expedientes.fcierre,
     spac_expedientes.identidadtitular,
     spac_expedientes.nifciftitular,
      spac_dt_intervinientes.nombre ,
     spac_dt_intervinientes.ndoc,
    spac_dt_intervinientes.localidad,
   spac_dt_intervinientes.direcciontelematica,
   spac_dt_intervinientes.tfno_fijo,
   spac_dt_intervinientes.tfno_movil
FROM
SPAC_EXPEDIENTES left outer join
     SPAC_DT_INTERVINIENTES ON SPAC_EXPEDIENTES.numexp = SPAC_DT_INTERVINIENTES.numexp
WHERE
     spac_expedientes.numexp = $P{NUM_EXP}]]></queryString>

	<field name="numexp" class="java.lang.String"/>
	<field name="asunto" class="java.lang.String"/>
	<field name="poblacion" class="java.lang.String"/>
	<field name="localizacion" class="java.lang.String"/>
	<field name="municipio" class="java.lang.String"/>
	<field name="fapertura" class="java.sql.Timestamp"/>
	<field name="fcierre" class="java.sql.Timestamp"/>
	<field name="identidadtitular" class="java.lang.String"/>
	<field name="nifciftitular" class="java.lang.String"/>
	<field name="nombre" class="java.lang.String"/>
	<field name="ndoc" class="java.lang.String"/>
	<field name="localidad" class="java.lang.String"/>
	<field name="direcciontelematica" class="java.lang.String"/>
	<field name="tfno_fijo" class="java.lang.String"/>
	<field name="tfno_movil" class="java.lang.String"/>


		<group  name="numexp" >
			<groupExpression><![CDATA[$F{numexp}]]></groupExpression>
			<groupHeader>
			<band height="27"  isSplitAllowed="true" >
				<staticText>
					<reportElement
						mode="Opaque"
						x="0"
						y="0"
						width="139"
						height="27"
						forecolor="#FFFFFF"
						backcolor="#000000"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16"/>
					</textElement>
				<text><![CDATA[Nº Expediente]]></text>
				</staticText>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						mode="Opaque"
						x="139"
						y="0"
						width="663"
						height="27"
						forecolor="#FFFFFF"
						backcolor="#000000"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{numexp}]]></textFieldExpression>
				</textField>
			</band>
			</groupHeader>
			<groupFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
			</groupFooter>
		</group>
		<group  name="asunto" >
			<groupExpression><![CDATA[$F{asunto}]]></groupExpression>
			<groupHeader>
			<band height="28"  isSplitAllowed="true" >
				<staticText>
					<reportElement
						mode="Opaque"
						x="0"
						y="0"
						width="139"
						height="27"
						backcolor="#CCCCFF"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16"/>
					</textElement>
				<text><![CDATA[Asunto]]></text>
				</staticText>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						mode="Opaque"
						x="139"
						y="0"
						width="663"
						height="27"
						backcolor="#CCCCFF"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{asunto}]]></textFieldExpression>
				</textField>
				<line direction="TopDown">
					<reportElement
						x="0"
						y="27"
						width="800"
						height="1"
						key="line"
						positionType="FixRelativeToBottom"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
			</band>
			</groupHeader>
			<groupFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
			</groupFooter>
		</group>
		<group  name="fapertura" >
			<groupExpression><![CDATA[$F{fapertura}]]></groupExpression>
			<groupHeader>
			<band height="28"  isSplitAllowed="true" >
				<staticText>
					<reportElement
						x="0"
						y="0"
						width="139"
						height="27"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16"/>
					</textElement>
				<text><![CDATA[F. Apertura	]]></text>
				</staticText>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="139"
						y="0"
						width="663"
						height="27"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[ieci.tdw.ispac.ispaclib.utils.DateUtil.format($F{fapertura})]]></textFieldExpression>
				</textField>
				<line direction="TopDown">
					<reportElement
						x="0"
						y="27"
						width="800"
						height="1"
						key="line"
						positionType="FixRelativeToBottom"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
			</band>
			</groupHeader>
			<groupFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
			</groupFooter>
		</group>
		<group  name="fcierre" >
			<groupExpression><![CDATA[$F{fcierre}]]></groupExpression>
			<groupHeader>
			<band height="28"  isSplitAllowed="true" >
				<staticText>
					<reportElement
						x="0"
						y="0"
						width="139"
						height="27"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16" isBold="false"/>
					</textElement>
				<text><![CDATA[F. Cierre]]></text>
				</staticText>
				<textField isStretchWithOverflow="false" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="139"
						y="0"
						width="663"
						height="27"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16" isBold="false"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[ieci.tdw.ispac.ispaclib.utils.DateUtil.format($F{fcierre})]]></textFieldExpression>
				</textField>
				<line direction="TopDown">
					<reportElement
						x="0"
						y="27"
						width="800"
						height="1"
						key="line"
						positionType="FixRelativeToBottom"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
			</band>
			</groupHeader>
			<groupFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
			</groupFooter>
		</group>
		<group  name="municipio" >
			<groupExpression><![CDATA[$F{municipio}]]></groupExpression>
			<groupHeader>
			<band height="28"  isSplitAllowed="true" >
				<textField isStretchWithOverflow="false" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="140"
						y="-1"
						width="662"
						height="28"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{municipio}]]></textFieldExpression>
				</textField>
				<staticText>
					<reportElement
						x="1"
						y="0"
						width="139"
						height="28"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16"/>
					</textElement>
				<text><![CDATA[Municipio]]></text>
				</staticText>
				<line direction="TopDown">
					<reportElement
						x="0"
						y="27"
						width="800"
						height="1"
						key="line"
						positionType="FixRelativeToBottom"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
			</band>
			</groupHeader>
			<groupFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
			</groupFooter>
		</group>
		<group  name="localizacion" >
			<groupExpression><![CDATA[$F{localizacion}]]></groupExpression>
			<groupHeader>
			<band height="28"  isSplitAllowed="true" >
				<textField isStretchWithOverflow="false" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="140"
						y="-1"
						width="661"
						height="28"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{localizacion}]]></textFieldExpression>
				</textField>
				<staticText>
					<reportElement
						x="-1"
						y="-1"
						width="139"
						height="28"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16"/>
					</textElement>
				<text><![CDATA[Localización]]></text>
				</staticText>
				<line direction="TopDown">
					<reportElement
						x="0"
						y="27"
						width="800"
						height="1"
						key="line"
						positionType="FixRelativeToBottom"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
			</band>
			</groupHeader>
			<groupFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
			</groupFooter>
		</group>
		<group  name="poblacion" >
			<groupExpression><![CDATA[$F{poblacion}]]></groupExpression>
			<groupHeader>
			<band height="45"  isSplitAllowed="true" >
				<textField isStretchWithOverflow="false" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="139"
						y="-1"
						width="663"
						height="29"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{poblacion}]]></textFieldExpression>
				</textField>
				<staticText>
					<reportElement
						x="0"
						y="-1"
						width="139"
						height="28"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16"/>
					</textElement>
				<text><![CDATA[Población]]></text>
				</staticText>
				<line direction="TopDown">
					<reportElement
						x="0"
						y="27"
						width="800"
						height="1"
						key="line"
						positionType="FixRelativeToBottom"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
			</band>
			</groupHeader>
			<groupFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
			</groupFooter>
		</group>
		<group  name="Titular" >
			<groupExpression><![CDATA[$V{PAGE_NUMBER}]]></groupExpression>
			<groupHeader>
			<band height="50"  isSplitAllowed="true" >
				<staticText>
					<reportElement
						mode="Opaque"
						x="1"
						y="0"
						width="799"
						height="30"
						backcolor="#CCCCCC"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Center">
						<font size="18" isBold="true"/>
					</textElement>
				<text><![CDATA[Titular]]></text>
				</staticText>
			</band>
			</groupHeader>
			<groupFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
			</groupFooter>
		</group>
		<group  name="nifciftitular" >
			<groupExpression><![CDATA[$F{nifciftitular}]]></groupExpression>
			<groupHeader>
			<band height="64"  isSplitAllowed="true" >
				<staticText>
					<reportElement
						x="1"
						y="28"
						width="136"
						height="28"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="14"/>
					</textElement>
				<text><![CDATA[Nº Doc ]]></text>
				</staticText>
				<textField isStretchWithOverflow="false" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="138"
						y="28"
						width="662"
						height="28"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{nifciftitular}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="137"
						y="0"
						width="663"
						height="28"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{identidadtitular}]]></textFieldExpression>
				</textField>
				<staticText>
					<reportElement
						x="1"
						y="0"
						width="136"
						height="28"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="14"/>
					</textElement>
				<text><![CDATA[Nombre]]></text>
				</staticText>
			</band>
			</groupHeader>
			<groupFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
			</groupFooter>
		</group>
		<group  name="Lista participantes" >
			<groupExpression><![CDATA[$P{REPORT_PARAMETERS_MAP}]]></groupExpression>
			<groupHeader>
			<band height="57"  isSplitAllowed="true" >
				<staticText>
					<reportElement
						mode="Opaque"
						x="1"
						y="0"
						width="799"
						height="30"
						backcolor="#CCCCCC"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Center">
						<font size="18" isBold="true"/>
					</textElement>
				<text><![CDATA[Listado de Participantes]]></text>
				</staticText>
				<staticText>
					<reportElement
						x="1"
						y="30"
						width="100"
						height="20"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="14" isBold="false"/>
					</textElement>
				<text><![CDATA[Nº Doc]]></text>
				</staticText>
				<staticText>
					<reportElement
						x="265"
						y="30"
						width="139"
						height="20"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="14" isBold="false"/>
					</textElement>
				<text><![CDATA[Localidad]]></text>
				</staticText>
				<staticText>
					<reportElement
						x="404"
						y="30"
						width="196"
						height="20"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="14" isBold="false"/>
					</textElement>
				<text><![CDATA[Dirección telemática]]></text>
				</staticText>
				<staticText>
					<reportElement
						x="599"
						y="30"
						width="100"
						height="20"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="14" isBold="false"/>
					</textElement>
				<text><![CDATA[Tlfn Fijo]]></text>
				</staticText>
				<staticText>
					<reportElement
						x="699"
						y="30"
						width="100"
						height="20"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="14" isBold="false"/>
					</textElement>
				<text><![CDATA[Tlfn Movil]]></text>
				</staticText>
				<staticText>
					<reportElement
						x="102"
						y="30"
						width="161"
						height="20"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="14" isBold="false"/>
					</textElement>
				<text><![CDATA[Nombre]]></text>
				</staticText>
			</band>
			</groupHeader>
			<groupFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
			</groupFooter>
		</group>
		<background>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</background>
		<title>
			<band height="53"  isSplitAllowed="true" >
				<line direction="TopDown">
					<reportElement
						x="0"
						y="8"
						width="800"
						height="1"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<line direction="TopDown">
					<reportElement
						x="0"
						y="51"
						width="800"
						height="1"
						key="line"
						positionType="FixRelativeToBottom"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<staticText>
					<reportElement
						x="65"
						y="13"
						width="593"
						height="35"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Center">
						<font size="26" isBold="true"/>
					</textElement>
				<text><![CDATA[Ficha del expediente]]></text>
				</staticText>
			</band>
		</title>
		<pageHeader>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</pageHeader>
		<columnHeader>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</columnHeader>
		<detail>
			<band height="30"  isSplitAllowed="true" >
				<textField isStretchWithOverflow="true" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="102"
						y="0"
						width="159"
						height="20"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{nombre}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="1"
						y="0"
						width="100"
						height="20"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{ndoc}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="265"
						y="0"
						width="135"
						height="20"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{localidad}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="404"
						y="0"
						width="190"
						height="20"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{direcciontelematica}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="599"
						y="0"
						width="100"
						height="20"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{tfno_fijo}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="699"
						y="0"
						width="100"
						height="20"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{tfno_movil}]]></textFieldExpression>
				</textField>
			</band>
		</detail>
		<columnFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</columnFooter>
		<pageFooter>
			<band height="26"  isSplitAllowed="true" >
				<textField isStretchWithOverflow="false" pattern="" isBlankWhenNull="false" evaluationTime="Report" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="475"
						y="6"
						width="36"
						height="19"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="10"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA["" + $V{PAGE_NUMBER}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" pattern="" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="301"
						y="6"
						width="170"
						height="19"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Right">
						<font size="10"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA["Página " + $V{PAGE_NUMBER} + " de "]]></textFieldExpression>
				</textField>
			</band>
		</pageFooter>
		<summary>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</summary>
</jasperReport>',
fecha = current_timestamp
where id=2;


--
-- Recursos modificados
--
UPDATE SPAC_CT_ENTIDADES_RESOURCES SET CLAVE = 'NVERSION' WHERE ID_ENT = 1 AND CLAVE = 'VERSION';
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'IDDIRECCIONPOSTAL', 'Id. de Dirección Postal');
DELETE FROM SPAC_CT_ENTIDADES_RESOURCES WHERE ID_ENT = 2 AND CLAVE = 'NDOC';
DELETE FROM SPAC_CT_ENTIDADES_RESOURCES WHERE ID_ENT = 2 AND CLAVE = 'NUM_ACTO';
DELETE FROM SPAC_CT_ENTIDADES_RESOURCES WHERE ID = 153;
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'ID_NOTIFICACION', 'Id. de Notificación');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'AUTOR', 'Autor');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'IDDIRECCIONPOSTAL', 'Id. de Dirección Postal');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'ID_SUBPROCESO', 'Id. de Subproceso');


--
-- Recursos para direcciones en expediente e intervinientes
--
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'LBL_LIBRE', 'Libre');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'LBL_CONFIRMADA', 'Verificada');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'SEP_DIRECCIONES', 'DIRECCION NOTIFICACIÓN');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'SEP_DIRECCIONES', 'DIRECCION NOTIFICACIÓN');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'LBL_CONFIRMADA', 'Verificada');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'LBL_LIBRE', 'Libre');
 

--
-- Formulario para expedientes
--
UPDATE spac_ct_aplicaciones SET frm_jsp = '<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center" onclick="showTab(1)">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SPAC_EXPEDIENTES_TAB_DATOS_EXPEDIENTE)" />
</nobr>
</td>
<td width="5px"><img height="1" width="5px" src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
<td class="unselect" id="tdlink2" align="center" onclick="document.defaultForm.name = ''Expedientes'';if (validateExpedientes(document.defaultForm)){showTab(2);}">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SPAC_EXPEDIENTES_TAB_INFORMACION_ADICIONAL)" />
</nobr>
</td>
</tr>
</table>

<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
<tr>
<td><img height="8" src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
<tr>
<td>

<!-- BLOQUE1 DE CAMPOS -->
<div style="display:block" id="block1">
<div id="dataBlock_SPAC_EXPEDIENTES_TAB_DATOS_EXPEDIENTE" style="position: relative; height: 710px; width: 600px;">
<div id="label_SPAC_EXPEDIENTES:NUMEXP" style="position: absolute; top: 23px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:NUMEXP)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:NUMEXP" style="position: absolute; top: 20px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:NUMEXP)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="30" maxlength="30" tabindex="101">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:FINICIOPLAZO" style="position: absolute; top: 54px; left: 391px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:FINICIOPLAZO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:FINICIOPLAZO" style="position: absolute; top: 51px; left: 505px;">
<nobr>
<ispac:htmlTextCalendar property="property(SPAC_EXPEDIENTES:FINICIOPLAZO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="103">
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:NREG" style="position: absolute; top: 149px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:NREG)" />:</nobr>
</div>

<div id="data_SPAC_EXPEDIENTES:NREG" style="position: absolute; top: 146px; left: 170px;">
<nobr>
<c:choose>
<c:when test="${!empty sicresConnectorClass}">
	<ispac:htmlTextImageFrame property="property(SPAC_EXPEDIENTES:NREG)"
				  readonly="false"
				  readonlyTag="false"
				  propertyReadonly="readonly"
				  styleClass="input"
				  styleClassReadonly="inputReadOnly"
				  size="25"
				  maxlength="16"
			  	  id="SEARCH_SPAC_EXPEDIENTES_NREG"
				  target="workframe"
			  	  action="searchInputRegistry.do" 
			  	  image="img/search-mg.gif" 
			  	  titleKeyLink="search.intray" 
			  	  showFrame="true"
			  	  inputField="SPAC_EXPEDIENTES:NREG"
			  	  width="500"
			  	  height="300"
			  	  jsDelete="delete_EXPEDIENT_SEARCH_INPUT_REGISTRY"
			  	  titleKeyImageDelete="forms.exp.title.delete.data.register" tabindex="105">
		
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:ASUNTO)" property="ASUNTO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:FREG)" property="FREG"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:TIPOPERSONA)" property="TIPOPERSONA"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:IDTITULAR)" property="IDTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:NIFCIFTITULAR)" property="NIFCIFTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)" property="IDENTIDADTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:DOMICILIO)" property="DOMICILIO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:CPOSTAL)" property="CPOSTAL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:CIUDAD)" property="CIUDAD"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:REGIONPAIS)" property="REGIONPAIS"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:TFNOFIJO)" property="TFNOFIJO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:TFNOMOVIL)" property="TFNOMOVIL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)" property="TIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTOTIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="JAVASCRIPT" property="accept_EXPEDIENT_SEARCH_INPUT_REGISTRY"/>
		
	</ispac:htmlTextImageFrame>
</c:when>
<c:otherwise>
	<ispac:htmlText property="property(SPAC_EXPEDIENTES:NREG)" readonly="false" styleClass="input" size="25" maxlength="16" tabindex="105"/>
</c:otherwise>
</c:choose>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:FREG" style="position: absolute; top: 151px; left: 392px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:FREG)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:FREG" style="position: absolute; top: 148px; left: 505px;">
<nobr>
<c:choose>
<c:when test="${!empty sicresConnectorClass}">
<ispac:htmlTextCalendar property="property(SPAC_EXPEDIENTES:FREG)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="106">
</ispac:htmlTextCalendar>
</c:when>
<c:otherwise>
<ispac:htmlTextCalendar property="property(SPAC_EXPEDIENTES:FREG)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="106">
</ispac:htmlTextCalendar>
</c:otherwise>
</c:choose>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:SEP_INTERESADO_PRINCIPAL" style="position: absolute; top: 285px; left: 10px; width: 620px" class="textbar">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SEP_INTERESADO_PRINCIPAL)" /></nobr>
<hr class="formbar"/>
</div>

<c:choose>
<c:when test="${!empty thirdPartyAPIClass}">
<div id="label_SPAC_EXPEDIENTES:LBL_VALIDADO" style="position: absolute; top: 324px; left: 10px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="validatedThirdParty" onclick="javascript: clickValidatedThirdParty();" checked="checked" tabindex="110"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:LBL_NO_VALIDADO" style="position: absolute; top: 324px; left: 100px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="notValidatedThirdParty" onclick="javascript: clickNotValidatedThirdParty();" tabindex="111"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_NO_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:NIFCIFTITULAR" style="position: absolute; top: 357px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:NIFCIFTITULAR)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:NIFCIFTITULAR" style="position: absolute; top: 354px; left: 170px;">
<nobr>
	<ispac:htmlTextImageFrame property="property(SPAC_EXPEDIENTES:NIFCIFTITULAR)"
				  readonly="false"
				  readonlyTag="false"
				  propertyReadonly="readonly"
				  styleClass="input"
				  styleClassReadonly="inputReadOnly"
				  size="34"
				  maxlength="16"
			  	  id="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR"
				  target="workframe"
			  	  action="searchThirdParty.do" 
			  	  image="img/search-mg.gif" 
			  	  titleKeyLink="search.thirdparty" 
			  	  showFrame="true"
			  	  inputField="SPAC_EXPEDIENTES:NIFCIFTITULAR"
			  	  jsDelete="delete_EXPEDIENT_SEARCH_THIRD_PARTY"
			  	  titleKeyImageDelete="forms.exp.title.delete.data.thirdParty"
			  	  jsExecuteFrame="selectThirdParty" 
				  tabindex="112"
				  >

		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:TIPOPERSONA)" property="TIPOPERSONA"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:IDTITULAR)" property="IDTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)" property="IDENTIDADTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:DOMICILIO)" property="DOMICILIO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:CPOSTAL)" property="CPOSTAL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:CIUDAD)" property="CIUDAD"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:REGIONPAIS)" property="REGIONPAIS"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:TFNOFIJO)" property="TFNOFIJO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:TFNOMOVIL)" property="TFNOMOVIL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:IDDIRECCIONPOSTAL)" property="IDDIRECCIONPOSTAL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)" property="TIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTOTIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="JAVASCRIPT" property="return_EXPEDIENT_SEARCH_THIRD_PARTY"/>
		
	</ispac:htmlTextImageFrame>

		<ispac:imageframe 
					  id="SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS"
					  target="workframe"
					  action="searchPostalAddressesThirdParty.do" 
					  image="img/icon_barra_3.gif" 
					  showFrame="true"
					  inputField="SPAC_EXPEDIENTES:IDTITULAR"
					  jsShowFrame="show_SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS" 
					  >
			<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS" id="property(SPAC_EXPEDIENTES:IDDIRECCIONPOSTAL)" property="IDDIRECCIONPOSTAL"/>
			<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS" id="property(SPAC_EXPEDIENTES:DOMICILIO)" property="DOMICILIO"/>
			<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS" id="property(SPAC_EXPEDIENTES:CPOSTAL)" property="CPOSTAL"/>
			<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS" id="property(SPAC_EXPEDIENTES:CIUDAD)" property="CIUDAD"/>
			<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS" id="property(SPAC_EXPEDIENTES:REGIONPAIS)" property="REGIONPAIS"/>
			<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS" id="property(SPAC_EXPEDIENTES:TFNOFIJO)" property="TFNOFIJO"/>

		</ispac:imageframe>
		<ispac:imageframe 
					  id="SEARCH_SPAC_EXPEDIENTES_ELECTRONIC_ADDRESS"
					  target="workframe"
					  action="searchElectronicAddressesThirdParty.do" 
					  image="img/icon_barra_2.gif" 
					  showFrame="true"
					  inputField="SPAC_EXPEDIENTES:IDTITULAR"
					  jsShowFrame="show_SEARCH_SPAC_EXPEDIENTES_ELECTRONIC_ADDRESS" 
					  >
					  
			<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_ELECTRONIC_ADDRESS" id="property(SPAC_EXPEDIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		</ispac:imageframe>

		<script language=''JavaScript'' type=''text/javascript''><!--
			function show_SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS(target, action, width, height, msgConfirm, doSubmit, needToConfirm) {
				direccion = document.defaultForm.elements[ ''property(SPAC_EXPEDIENTES:DOMICILIO)'' ];
				if (direccion.readOnly) {
					idtitular = document.forms[0].elements[ ''property(SPAC_EXPEDIENTES:IDTITULAR)'' ];
					if (idtitular.value != '''') {
						showFrame(target, action, width, height, msgConfirm, doSubmit, needToConfirm);
					}
					else{jAlert(''<bean:message key="terceros.interested.notSelected"/>'', ''<bean:message key="common.alert"/>'');}
				}

			}
			
			function show_SEARCH_SPAC_EXPEDIENTES_ELECTRONIC_ADDRESS(target, action, width, height, msgConfirm, doSubmit, needToConfirm) {
				idtitular = document.forms[0].elements[ ''property(SPAC_EXPEDIENTES:IDTITULAR)'' ];
				if (idtitular.value != '''') {
					showFrame(target, action, width, height, msgConfirm, doSubmit, needToConfirm);
				}
				else{jAlert(''<bean:message key="terceros.interested.notSelected"/>'', ''<bean:message key="common.alert"/>'');}
			}
			//-->
		</script>



</nobr>
</div>


<div id="label_SPAC_EXPEDIENTES:SEP_DIRECCIONES" style="position: absolute; top: 422px; left: 10px; width: 200px" class="textbar1">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SEP_DIRECCIONES)" /></nobr>
<hr class="formbar1"/>
</div>


<div id="label_SPAC_EXPEDIENTES:LBL_VALIDADO_POSTALADDRESS" style="position: absolute; top: 447px; left: 10px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationPostalAddressThirdParty" id="validatedPostalAddressThirdParty" onclick="javascript: clickValidatedPostalAddressThirdParty();" checked="checked" tabindex="110"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_CONFIRMADA)" /></nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:LBL_NO_VALIDADO_POSTALADDRESS" style="position: absolute; top: 447px; left: 100px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationPostalAddressThirdParty" id="notValidatedPostalAddressThirdParty" onclick="javascript: clickNotValidatedPostalAddressThirdParty();" tabindex="111"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_LIBRE)" /></nobr>
</div>

</c:when>
<c:otherwise>
<div id="label_SPAC_EXPEDIENTES:LBL_VALIDADO" style="position: absolute; top: 324px; left: 10px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="validatedThirdParty" disabled="true" tabindex="110"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:LBL_NO_VALIDADO" style="position: absolute; top: 324px; left: 100px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="notValidatedThirdParty" checked="checked" disabled="true" tabindex="111"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_NO_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:NIFCIFTITULAR" style="position: absolute; top: 357px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:NIFCIFTITULAR)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:NIFCIFTITULAR" style="position: absolute; top: 354px; left: 170px;">
	<ispac:htmlText property="property(SPAC_EXPEDIENTES:NIFCIFTITULAR)" readonly="false" styleClass="input" size="34" maxlength="16" tabindex="112"/>
</div>
<div id="label_SPAC_EXPEDIENTES:IDENTIDADTITULAR" style="position: absolute; top: 390px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:IDENTIDADTITULAR)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:IDENTIDADTITULAR" style="position: absolute; top: 387px; left: 170px;">
<ispac:htmlTextarea property="property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="85" tabindex="113">
</ispac:htmlTextarea>
</div>

<div id="label_SPAC_EXPEDIENTES:SEP_DIRECCIONES" style="position: absolute; top: 422px; left: 10px; width: 200px" class="textbar1">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SEP_DIRECCIONES)" /></nobr>
<hr class="formbar1"/>
</div>


<div id="label_SPAC_EXPEDIENTES:LBL_VALIDADO_POSTALADDRESS" style="position: absolute; top: 447px; left: 10px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationPostalAddressThirdParty" id="validatedPostalAddressThirdParty" tabindex="110" disabled="true"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_CONFIRMADA)" /></nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:LBL_NO_VALIDADO_POSTALADDRESS" style="position: absolute; top: 447px; left: 100px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationPostalAddressThirdParty" id="notValidatedPostalAddressThirdParty" checked="checked" disabled="true" tabindex="111"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_LIBRE)" /></nobr>
</div>



</c:otherwise>
</c:choose>

<div id="label_SPAC_EXPEDIENTES:DOMICILIO" style="position: absolute; top: 480px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:DOMICILIO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:DOMICILIO" style="position: absolute; top: 477px; left: 170px;">
<ispac:htmlTextarea property="property(SPAC_EXPEDIENTES:DOMICILIO)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="85" tabindex="114" >
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_EXPEDIENTES:CIUDAD" style="position: absolute; top: 514px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:CIUDAD)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:CIUDAD" style="position: absolute; top: 511px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:CIUDAD)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="30" maxlength="64" tabindex="115">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:REGIONPAIS" style="position: absolute; top: 547px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:REGIONPAIS)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:REGIONPAIS" style="position: absolute; top: 544px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:REGIONPAIS)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="64" tabindex="117">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:CPOSTAL" style="position: absolute; top: 514px; left: 370px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:CPOSTAL)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:CPOSTAL" style="position: absolute; top: 511px; left: 470px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:CPOSTAL)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="10" maxlength="5" tabindex="116">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:IDENTIDADTITULAR" style="position: absolute; top: 390px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:IDENTIDADTITULAR)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:IDENTIDADTITULAR" style="position: absolute; top: 387px; left: 170px;">
<ispac:htmlTextarea property="property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="85" tabindex="113">
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_EXPEDIENTES:ROLTITULAR" style="position: absolute; top: 690px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:ROLTITULAR)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:ROLTITULAR" style="position: absolute; top: 687px; left: 170px;">
<html:hidden property="property(SPAC_EXPEDIENTES:ROLTITULAR)" />
<nobr>
<ispac:htmlTextImageFrame property="property(ROLTITULAR_SPAC_TBL_002:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="34" id="SEARCH_SPAC_EXPEDIENTES_ROLTITULAR" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_002" image="img/search-mg.gif" titleKeyLink="select.rol" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="122">
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_ROLTITULAR" id="property(SPAC_EXPEDIENTES:ROLTITULAR)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_ROLTITULAR" id="property(ROLTITULAR_SPAC_TBL_002:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:ASUNTO" style="position: absolute; top: 83px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:ASUNTO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:ASUNTO" style="position: absolute; top: 80px; left: 170px;">
<ispac:htmlTextarea property="property(SPAC_EXPEDIENTES:ASUNTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="4" cols="85" tabindex="104">
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_EXPEDIENTES:FORMATERMINACION" style="position: absolute; top: 181px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:FORMATERMINACION)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:FORMATERMINACION" style="position: absolute; top: 178px; left: 170px;">
<html:hidden property="property(SPAC_EXPEDIENTES:FORMATERMINACION)" />
<nobr>
<ispac:htmlTextImageFrame property="property(FORMATERMINACION_SPAC_TBL_003:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" id="SEARCH_SPAC_EXPEDIENTES_FORMATERMINACION" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_003" image="img/search-mg.gif" titleKeyLink="select.termination" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="107">
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_FORMATERMINACION" id="property(SPAC_EXPEDIENTES:FORMATERMINACION)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_FORMATERMINACION" id="property(FORMATERMINACION_SPAC_TBL_003:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:ESTADOADM" style="position: absolute; top: 214px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:ESTADOADM)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:ESTADOADM" style="position: absolute; top: 211px; left: 170px;">
<html:hidden property="property(SPAC_EXPEDIENTES:ESTADOADM)" />
<nobr>
<ispac:htmlTextImageFrame property="property(ESTADOADM_SPAC_TBL_004:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" id="SEARCH_SPAC_EXPEDIENTES_ESTADOADM" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_004" image="img/search-mg.gif" titleKeyLink="select.estadoadm" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="108">
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_ESTADOADM" id="property(SPAC_EXPEDIENTES:ESTADOADM)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_ESTADOADM" id="property(ESTADOADM_SPAC_TBL_004:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:FAPERTURA" style="position: absolute; top: 52px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:FAPERTURA)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:FAPERTURA" style="position: absolute; top: 49px; left: 170px;">
<nobr>
<ispac:htmlTextCalendar property="property(SPAC_EXPEDIENTES:FAPERTURA)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="102">
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO" style="position: absolute; top: 655px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO" style="position: absolute; top: 652px; left: 170px;">
<html:hidden property="property(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)" />
<nobr>
<ispac:htmlTextImageFrame property="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="34" id="SEARCH_SPAC_EXPEDIENTES_TIPODIRECCIONINTERESADO" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_005" image="img/search-mg.gif" titleKeyLink="select.tipoDireccionNotificacion" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="121">
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_TIPODIRECCIONINTERESADO" id="property(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_TIPODIRECCIONINTERESADO" id="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:SECCIONINICIADORA" style="position: absolute; top: 247px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SECCIONINICIADORA)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:SECCIONINICIADORA" style="position: absolute; top: 244px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:SECCIONINICIADORA)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" maxlength="250" tabindex="109">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:TFNOFIJO" style="position: absolute; top: 583px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:TFNOFIJO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:TFNOFIJO" style="position: absolute; top: 580px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:TFNOFIJO)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="32" tabindex="118">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:TFNOMOVIL" style="position: absolute; top: 583px; left: 370px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:TFNOMOVIL)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:TFNOMOVIL" style="position: absolute; top: 580px; left: 470px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:TFNOMOVIL)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="32" tabindex="119">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:DIRECCIONTELEMATICA" style="position: absolute; top: 620px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:DIRECCIONTELEMATICA)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:DIRECCIONTELEMATICA" style="position: absolute; top: 617px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:DIRECCIONTELEMATICA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="60" tabindex="120">
</ispac:htmlText>
</div>
</div>
</div>

<!-- BLOQUE2 DE CAMPOS -->
<div style="display:none" id="block2">
<div id="dataBlock_SPAC_EXPEDIENTES_TAB_INFORMACION_ADICIONAL" style="position: relative; height: 280px; width: 600px;">
<div id="label_SPAC_EXPEDIENTES:UTRAMITADORA" style="position: absolute; top: 23px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:UTRAMITADORA)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:UTRAMITADORA" style="position: absolute; top: 20px; left: 170px;">
<nobr>
	<ispac:htmlTextImageFrame property="property(SPAC_EXPEDIENTES:UTRAMITADORA)"
				  readonly="true"
				  readonlyTag="false"
				  propertyReadonly="readonly"
				  styleClass="input"
				  styleClassReadonly="inputReadOnly"
				  size="50"
			  	  id="SEARCH_SPAC_EXPEDIENTES_UTRAMITADORA"
				  target="workframe"
			  	  action="viewUsersManager.do?captionkey=select.unidadTramitadora&noviewusers=true&noviewgroups=true"
			  	  image="img/search-mg.gif" 
			  	  titleKeyLink="select.unidadTramitadora" 
			  	  showFrame="true" tabindex="201">
						  	  
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_UTRAMITADORA" id="property(SPAC_EXPEDIENTES:UTRAMITADORA)" property="NAME"/>
		
	</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:FCIERRE" style="position: absolute; top: 58px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:FCIERRE)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:FCIERRE" style="position: absolute; top: 55px; left: 170px;">
<nobr>
<ispac:htmlTextCalendar property="property(SPAC_EXPEDIENTES:FCIERRE)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="202">
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:HAYRECURSO" style="position: absolute; top: 58px; left: 306px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:HAYRECURSO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:HAYRECURSO" style="position: absolute; top: 55px; left: 364px;">
<ispac:htmlCheckbox property="property(SPAC_EXPEDIENTES:HAYRECURSO)" readonly="false" propertyReadonly="readonly" valueChecked="SI" styleClassCheckbox="inputSelectP" tabindex="203"></ispac:htmlCheckbox>
</div>
<div id="label_SPAC_EXPEDIENTES:OBSERVACIONES" style="position: absolute; top: 93px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:OBSERVACIONES)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:OBSERVACIONES" style="position: absolute; top: 90px; left: 170px;">
<ispac:htmlTextarea property="property(SPAC_EXPEDIENTES:OBSERVACIONES)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="85" tabindex="204">
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_EXPEDIENTES:SEP_TERRITORIO" style="position: absolute; top: 140px; left: 10px; width: 620px" class="textbar">
<nobr><bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SEP_TERRITORIO)" />
<hr class="formbar"/>
</div>
<div id="label_SPAC_EXPEDIENTES:POBLACION" style="position: absolute; top: 183px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:POBLACION)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:POBLACION" style="position: absolute; top: 180px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:POBLACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" maxlength="64" tabindex="205">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:MUNICIPIO" style="position: absolute; top: 213px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:MUNICIPIO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:MUNICIPIO" style="position: absolute; top: 210px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:MUNICIPIO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" maxlength="64" tabindex="206">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:LOCALIZACION" style="position: absolute; top: 243px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LOCALIZACION)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:LOCALIZACION" style="position: absolute; top: 240px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:LOCALIZACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" maxlength="256" tabindex="207">
</ispac:htmlText>
</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>',
frm_version = frm_version + 1
WHERE id = 1;


--
-- Formulario para intervinientes
--
UPDATE spac_ct_aplicaciones SET frm_jsp = '<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:SPAC_DT_INTERVINIENTES_TAB_DATOS_PARTICIPANTE)" />
</nobr>
</td>
</tr>
</table>

<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
<tr>
<td><img height="8" src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
<tr>
<td>

<!-- BLOQUE1 DE CAMPOS -->
<div style="display:block" id="block1">
<div id="dataBlock_SPAC_DT_INTERVINIENTES_TAB_DATOS_PARTICIPANTE" style="position: relative; height: 400px; width: 600px;">

<c:choose>
<c:when test="${!empty thirdPartyAPIClass}">
<div id="label_SPAC_DT_INTERVINIENTES:LBL_VALIDADO" style="position: absolute; top: 5px; left: 10px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="validatedThirdParty" onclick="javascript: clickValidatedThirdParty();" checked="checked" tabindex="101"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:LBL_NO_VALIDADO" style="position: absolute; top: 5px; left: 100px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="notValidatedThirdParty" onclick="javascript: clickNotValidatedThirdParty();" tabindex="102"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_NO_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:NDOC" style="position: absolute; top: 37px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:NDOC)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:NDOC" style="position: absolute; top: 34px; left: 175px;">
<nobr>
	<ispac:htmlTextImageFrame property="property(SPAC_DT_INTERVINIENTES:NDOC)"
				  readonly="false"
				  readonlyTag="false"
				  propertyReadonly="readonly"
				  styleClass="input"
				  styleClassReadonly="inputReadOnly"
				  size="34"
				  maxlength="16"
			  	  id="THIRD_SEARCH_THIRD_PARTY"
				  target="workframe"
			  	  action="searchThirdParty.do" 
			  	  image="img/search-mg.gif" 
			  	  titleKeyLink="search.thirdparty" 
			  	  showFrame="false"
			  	  inputField="SPAC_DT_INTERVINIENTES:NDOC"
			  	  jsDelete="delete_THIRD_SEARCH_THIRD_PARTY"
			  	  titleKeyImageDelete="forms.participantes.title.delete.data.thirdParty"
			  	  jsExecuteFrame="selectThirdParty" 
				  tabindex="112"
				  >

		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:TIPO_PERSONA)" property="TIPOPERSONA"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:ID_EXT)" property="IDTITULAR"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:NOMBRE)" property="IDENTIDADTITULAR"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:DIRNOT)" property="DOMICILIO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:C_POSTAL)" property="CPOSTAL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:LOCALIDAD)" property="CIUDAD"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:CAUT)" property="REGIONPAIS"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:TFNO_FIJO)" property="TFNOFIJO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:TFNO_MOVIL)" property="TFNOMOVIL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:IDDIRECCIONPOSTAL)" property="IDDIRECCIONPOSTAL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" property="TIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTOTIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="JAVASCRIPT" property="return_THIRD_SEARCH_THIRD_PARTY"/>
		
	</ispac:htmlTextImageFrame>

		<ispac:imageframe 
					  id="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS"
					  target="workframe"
					  action="searchPostalAddressesThirdParty.do" 
					  image="img/icon_barra_3.gif" 
					  showFrame="true"
					  inputField="SPAC_DT_INTERVINIENTES:ID_EXT"
					  jsShowFrame="show_SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" 
					  >
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:IDDIRECCIONPOSTAL)" property="IDDIRECCIONPOSTAL"/>
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:DIRNOT)" property="DOMICILIO"/>
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:C_POSTAL)" property="CPOSTAL"/>
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:LOCALIDAD)" property="CIUDAD"/>
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:CAUT)" property="REGIONPAIS"/>
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:TFNO_FIJO)" property="TFNOFIJO"/>

		</ispac:imageframe>

		<ispac:imageframe 
					  id="SEARCH_SPAC_DT_INTERVINIENTES_ELECTRONIC_ADDRESS"
					  target="workframe"
					  action="searchElectronicAddressesThirdParty.do" 
					  image="img/icon_barra_2.gif" 
					  showFrame="true"
					  inputField="SPAC_DT_INTERVINIENTES:ID_EXT"
					  jsShowFrame="show_SEARCH_SPAC_DT_INTERVINIENTES_ELECTRONIC_ADDRESS" 
					  >
					  
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_ELECTRONIC_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		</ispac:imageframe>

		<script language=''JavaScript'' type=''text/javascript''><!--
			function show_SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS(target, action, width, height, msgConfirm, doSubmit, needToConfirm) {
				direccion = document.defaultForm.elements[ ''property(SPAC_DT_INTERVINIENTES:DIRNOT)'' ];
				if (direccion.readOnly) {
					idtitular = document.forms[0].elements[ ''property(SPAC_DT_INTERVINIENTES:ID_EXT)'' ];
					if (idtitular.value != '''') {
						showFrame(target, action, width, height, msgConfirm, doSubmit, needToConfirm);
					}
					else{jAlert(''<bean:message key="terceros.thirdnotSelected"/>'', ''<bean:message key="common.alert"/>'');}
				}
			}
			
			function show_SEARCH_SPAC_DT_INTERVINIENTES_ELECTRONIC_ADDRESS(target, action, width, height, msgConfirm, doSubmit, needToConfirm) {
				idtitular = document.forms[0].elements[ ''property(SPAC_DT_INTERVINIENTES:ID_EXT)'' ];
				if (idtitular.value != '''') {
					showFrame(target, action, width, height, msgConfirm, doSubmit, needToConfirm);
				}
				else{jAlert(''<bean:message key="terceros.thirdnotSelected"/>'' , ''<bean:message key="common.alert"/>'');}
			}
			//-->
		</script>
</nobr>






</div>


<div id="label_SPAC_DT_INTERVINIENTES:NOMBRE" style="position: absolute; top: 67px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:NOMBRE)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:NOMBRE" style="position: absolute; top: 64px; left: 175px;">
<ispac:htmlTextarea property="property(SPAC_DT_INTERVINIENTES:NOMBRE)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" tabindex="104">
</ispac:htmlTextarea>
</div>
</c:when>
<c:otherwise>
<div id="label_SPAC_DT_INTERVINIENTES:LBL_VALIDADO" style="position: absolute; top: 5px; left: 10px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="validatedThirdParty" disabled="true" tabindex="101"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:LBL_NO_VALIDADO" style="position: absolute; top: 5px; left: 100px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="notValidatedThirdParty" checked="checked" disabled="true" tabindex="102"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_NO_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:NDOC" style="position: absolute; top: 37px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:NDOC)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:NDOC" style="position: absolute; top: 34px; left: 175px;">
	<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:NDOC)" readonly="false" styleClass="input" size="30" maxlength="12" tabindex="103"/>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:NOMBRE" style="position: absolute; top: 67px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:NOMBRE)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:NOMBRE" style="position: absolute; top: 64px; left: 175px;">
<ispac:htmlTextarea property="property(SPAC_DT_INTERVINIENTES:NOMBRE)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" tabindex="104">
</ispac:htmlTextarea>
</div>
</c:otherwise>
</c:choose>


<div id="label_SPAC_DT_INTERVINIENTES:SEP_DIRECCIONES" style="position: absolute; top: 103px; left: 10px; width: 200px" class="textbar1">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:SEP_DIRECCIONES)" /></nobr>
<hr class="formbar1"/>
</div>


<div id="label_SPAC_DT_INTERVINIENTES:LBL_VALIDADO_POSTALADDRESS" style="position: absolute; top: 128px; left: 10px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationPostalAddressThirdParty" id="validatedPostalAddressThirdParty" onclick="javascript: clickValidatedPostalAddressThirdParty();" checked="checked" tabindex="110"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_CONFIRMADA)" /></nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:LBL_NO_VALIDADO_POSTALADDRESS" style="position: absolute; top: 128px; left: 100px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationPostalAddressThirdParty" id="notValidatedPostalAddressThirdParty" onclick="javascript: clickNotValidatedPostalAddressThirdParty();" tabindex="111"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_LIBRE)" /></nobr>
</div>






<div id="label_SPAC_DT_INTERVINIENTES:ROL" style="position: absolute; top: 371px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:ROL)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:ROL" style="position: absolute; top: 368px; left: 175px;">
<html:hidden property="property(SPAC_DT_INTERVINIENTES:ROL)" />
<nobr>
<ispac:htmlTextImageFrame property="property(ROL_SPAC_TBL_002:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" id="SEARCH_SPAC_DT_INTERVINIENTES_ROL" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_002" image="img/search-mg.gif" titleKeyLink="select.rol" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="113">
<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_ROL" id="property(SPAC_DT_INTERVINIENTES:ROL)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_ROL" id="property(ROL_SPAC_TBL_002:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:DIRNOT" style="position: absolute; top: 153px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:DIRNOT)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:DIRNOT" style="position: absolute; top: 150px; left: 175px;">
<ispac:htmlTextarea property="property(SPAC_DT_INTERVINIENTES:DIRNOT)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" tabindex="105">
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:C_POSTAL" style="position: absolute; top: 240px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:C_POSTAL)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:C_POSTAL" style="position: absolute; top: 237px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:C_POSTAL)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="10" maxlength="10" tabindex="107">
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:LOCALIDAD" style="position: absolute; top: 189px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LOCALIDAD)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:LOCALIDAD" style="position: absolute; top: 186px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:LOCALIDAD)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="150" tabindex="106">
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:CAUT" style="position: absolute; top: 216px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:CAUT)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:CAUT" style="position: absolute; top: 213px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:CAUT)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="150" tabindex="108">
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:TFNO_FIJO" style="position: absolute; top: 267px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:TFNO_FIJO)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:TFNO_FIJO" style="position: absolute; top: 264px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:TFNO_FIJO)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="32" tabindex="109">
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:TFNO_MOVIL" style="position: absolute; top: 293px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:TFNO_MOVIL)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:TFNO_MOVIL" style="position: absolute; top: 290px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:TFNO_MOVIL)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="32" tabindex="110">
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:TIPO_DIRECCION" style="position: absolute; top: 345px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:TIPO_DIRECCION" style="position: absolute; top: 342px; left: 175px;">
<html:hidden property="property(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" />
<nobr>
<ispac:htmlTextImageFrame property="property(TIPO_DIRECCION_SPAC_TBL_005:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="35" id="SEARCH_SPAC_DT_INTERVINIENTES_TIPO_DIRECCION" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_005" image="img/search-mg.gif" titleKeyLink="select.tipoDireccionNotificacion" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="112">
<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_TIPO_DIRECCION" id="property(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_TIPO_DIRECCION" id="property(TIPO_DIRECCION_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA" style="position: absolute; top: 319px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA" style="position: absolute; top: 316px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="60" tabindex="111">
</ispac:htmlText>
</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>',
frm_version = frm_version + 1
WHERE id = 3;

