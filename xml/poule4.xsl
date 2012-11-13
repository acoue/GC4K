<!--
Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
-->
<!-- $Id$ -->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" version="1.1" exclude-result-prefixes="fo">
<xsl:output method="xml" version="1.0" omit-xml-declaration="no" indent="yes"/>
<xsl:param name="date" select="''"/>
<xsl:param name="categorie" select="''"/>
<xsl:param name="titre" select="''"/>
<xsl:param name="combattant1" select="''"/>
<xsl:param name="combattant2" select="''"/>
<xsl:param name="combattant3" select="''"/>
<xsl:param name="combattant4" select="''"/>
<xsl:param name="club1" select="''"/>
<xsl:param name="club2" select="''"/>
<xsl:param name="club3" select="''"/>
<xsl:param name="club4" select="''"/>
<xsl:param name="numero" select="''"/>

	<!-- ========================= -->
	<!-- root element: projectteam -->
	<!-- ========================= -->
	<xsl:template match="competition">	
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="simpleA4" page-height="21cm" page-width="29.7cm" margin-top="1cm" margin-bottom="1cm" margin-left="1cm" margin-right="1cm">
					<fo:region-body/>
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="simpleA4">
				<fo:flow flow-name="xsl-region-body" >	
				
					<fo:block font-size="8pt">
						<fo:table table-layout="fixed" width="100%">
							<fo:table-column column-width="25%"/>
							<fo:table-column column-width="25%"/>
							<fo:table-column column-width="25%"/>
							<fo:table-column column-width="25%"/>
							<fo:table-body >
								<fo:table-row height="20px">
									<fo:table-cell padding="1px" number-rows-spanned="3"><fo:block display-align="center" text-align="left"><fo:external-graphic src="url('./xml/logocnk.jpg')"></fo:external-graphic></fo:block></fo:table-cell>
									<fo:table-cell padding="1px"><fo:block text-align="left" font-weight="bold"><xsl:value-of select="titre1"/></fo:block></fo:table-cell>
									<fo:table-cell padding="1px"><fo:block text-align="right" font-weight="bold">Date :</fo:block></fo:table-cell>
									<fo:table-cell padding="1px"><fo:block text-align="left" font-weight="bold"><xsl:value-of select="$date"/></fo:block></fo:table-cell>
								</fo:table-row>		
								<fo:table-row height="20px">
									<fo:table-cell padding="1px" number-columns-spanned="3"><fo:block text-align="left" font-weight="bold"><xsl:value-of select="titre2"/></fo:block></fo:table-cell>		
								</fo:table-row>		
								<fo:table-row height="20px">
									<fo:table-cell padding="1px"><fo:block text-align="left" font-weight="bold"><xsl:value-of select="titre3"/></fo:block></fo:table-cell>
									<fo:table-cell padding="1px"><fo:block text-align="right" font-weight="bold">Catégorie :</fo:block></fo:table-cell>
									<fo:table-cell padding="1px"><fo:block text-align="left" font-weight="bold"><xsl:value-of select="$categorie"/></fo:block></fo:table-cell>
								</fo:table-row>								
							</fo:table-body>
						</fo:table>
					</fo:block>
									
					<fo:block font-size="14pt" space-after="0.5cm" space-before="1cm" text-align="center" font-weight="bold"><xsl:value-of select="$titre"/></fo:block>
					<fo:block font-size="14pt" space-after="0.5cm" space-before="1cm" text-align="center" font-weight="bold">Poule n°<xsl:value-of select="$numero"/></fo:block>
					<fo:block font-size="10pt" space-after="0.5cm" space-before="1cm" text-align="left" >Poule de 4 : 1x2 - 3x4 - 1x4 - 1x3 - 2x3 - 2x4</fo:block>
					
					<fo:block font-size="10pt">
						<fo:table table-layout="fixed" width="100%">
							<fo:table-column column-width="30%"/>
							<fo:table-column column-width="4%"/>
							<fo:table-column column-width="10%"/>
							<fo:table-column column-width="10%"/>
							<fo:table-column column-width="10%"/>							
							<fo:table-column column-width="10%"/>							
							<fo:table-column column-width="10%"/>							
							<fo:table-column column-width="4%"/>							
							<fo:table-column column-width="12%"/>
							<fo:table-body >
								<fo:table-row height="20px">
									<fo:table-cell padding="1px"><fo:block text-align="center" font-weight="bold">NOM/CLUB</fo:block></fo:table-cell>
									<fo:table-cell><fo:block></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black"><fo:block text-align="center" font-weight="bold">1</fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black"><fo:block text-align="center" font-weight="bold">2</fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black"><fo:block text-align="center" font-weight="bold">3</fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black"><fo:block text-align="center" font-weight="bold">4</fo:block></fo:table-cell>
									<fo:table-cell border="1px solid black" number-columns-spanned="2"><fo:block></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" text-align="center"><fo:block text-align="center" font-weight="bold">Classement</fo:block></fo:table-cell>									
								</fo:table-row>
								
								<fo:table-row height="20px">
									<fo:table-cell padding="3px" number-rows-spanned="2" display-align="center" text-align="left" border-left="1px solid black" border-right="1px solid black" border-top="1px solid black"><fo:block font-weight="bold"><xsl:value-of select="$combattant1"/></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" number-rows-spanned="3" display-align="center" text-align="center"><fo:block font-weight="bold">1</fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" number-rows-spanned="3" background-color="silver"><fo:block></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" number-rows-spanned="3"><fo:block></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" number-rows-spanned="3"><fo:block></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" number-rows-spanned="3"><fo:block></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" display-align="center"><fo:block font-size="8pt">IPPON</fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black"><fo:block></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" number-rows-spanned="3"><fo:block></fo:block></fo:table-cell>							
								</fo:table-row>
								<fo:table-row height="20px">
									<fo:table-cell padding="1px" border="1px solid black" display-align="center"><fo:block font-size="8pt">Nb de victoires</fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black"><fo:block></fo:block></fo:table-cell>								
								</fo:table-row>
								<fo:table-row height="20px">
									<fo:table-cell padding="3px" border-left="1px solid black" border-right="1px solid black" border-bottom="1px solid black"><fo:block display-align="center" font-size="8pt"><xsl:value-of select="$club1"/></fo:block></fo:table-cell>									
									<fo:table-cell padding="1px" border="1px solid black" display-align="center"><fo:block font-size="8pt">Points</fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black"><fo:block></fo:block></fo:table-cell>									
								</fo:table-row>
								
								<fo:table-row height="20px">
									<fo:table-cell padding="3px" number-rows-spanned="2" display-align="center" text-align="left" border-left="1px solid black" border-right="1px solid black" border-top="1px solid black"><fo:block font-weight="bold"><xsl:value-of select="$combattant2"/></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" number-rows-spanned="3" display-align="center" text-align="center"><fo:block font-weight="bold">2</fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" number-rows-spanned="3"><fo:block></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" number-rows-spanned="3" background-color="silver"><fo:block></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" number-rows-spanned="3"><fo:block></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" number-rows-spanned="3"><fo:block></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" display-align="center"><fo:block font-size="8pt">IPPON</fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black"><fo:block></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" number-rows-spanned="3"><fo:block></fo:block></fo:table-cell>							
								</fo:table-row>
								<fo:table-row height="20px">
									<fo:table-cell padding="1px" border="1px solid black" display-align="center"><fo:block font-size="8pt">Nb de victoires</fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black"><fo:block></fo:block></fo:table-cell>								
								</fo:table-row>
								<fo:table-row height="20px">
									<fo:table-cell padding="3px" border-left="1px solid black" border-right="1px solid black" border-bottom="1px solid black"><fo:block display-align="center" font-size="8pt"><xsl:value-of select="$club2"/></fo:block></fo:table-cell>									
									<fo:table-cell padding="1px" border="1px solid black" display-align="center"><fo:block font-size="8pt">Points</fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black"><fo:block></fo:block></fo:table-cell>									
								</fo:table-row>
								
								<fo:table-row height="20px">
									<fo:table-cell padding="3px" number-rows-spanned="2" display-align="center" text-align="left" border-left="1px solid black" border-right="1px solid black" border-top="1px solid black"><fo:block font-weight="bold"><xsl:value-of select="$combattant3"/></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" number-rows-spanned="3" display-align="center" text-align="center"><fo:block font-weight="bold">3</fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" number-rows-spanned="3"><fo:block></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" number-rows-spanned="3"><fo:block></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" number-rows-spanned="3" background-color="silver"><fo:block></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" number-rows-spanned="3"><fo:block></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" display-align="center"><fo:block font-size="8pt">IPPON</fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black"><fo:block></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" number-rows-spanned="3"><fo:block></fo:block></fo:table-cell>							
								</fo:table-row>
								<fo:table-row height="20px">
									<fo:table-cell padding="1px" border="1px solid black" display-align="center"><fo:block font-size="8pt">Nb de victoires</fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black"><fo:block></fo:block></fo:table-cell>								
								</fo:table-row>
								<fo:table-row height="20px">
									<fo:table-cell padding="3px" border-left="1px solid black" border-right="1px solid black" border-bottom="1px solid black"><fo:block display-align="center" font-size="8pt"><xsl:value-of select="$club3"/></fo:block></fo:table-cell>									
									<fo:table-cell padding="1px" border="1px solid black" display-align="center"><fo:block font-size="8pt">Points</fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black"><fo:block></fo:block></fo:table-cell>								
								</fo:table-row>
								
								<fo:table-row height="20px">
									<fo:table-cell padding="3px" number-rows-spanned="2" display-align="center" text-align="left" border-left="1px solid black" border-right="1px solid black" border-top="1px solid black"><fo:block font-weight="bold"><xsl:value-of select="$combattant4"/></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" number-rows-spanned="3" display-align="center" text-align="center"><fo:block font-weight="bold">1</fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" number-rows-spanned="3"><fo:block></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" number-rows-spanned="3"><fo:block></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" number-rows-spanned="3"><fo:block></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" number-rows-spanned="3" background-color="silver"><fo:block></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" display-align="center"><fo:block font-size="8pt">IPPON</fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black"><fo:block></fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black" number-rows-spanned="3"><fo:block></fo:block></fo:table-cell>							
								</fo:table-row>
								<fo:table-row height="20px">
									<fo:table-cell padding="1px" border="1px solid black" display-align="center"><fo:block font-size="8pt">Nb de victoires</fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black"><fo:block></fo:block></fo:table-cell>								
								</fo:table-row>
								<fo:table-row height="20px">
									<fo:table-cell padding="3px" border-left="1px solid black" border-right="1px solid black" border-bottom="1px solid black"><fo:block display-align="center" font-size="8pt"><xsl:value-of select="$club4"/></fo:block></fo:table-cell>									
									<fo:table-cell padding="1px" border="1px solid black" display-align="center"><fo:block font-size="8pt">Points</fo:block></fo:table-cell>
									<fo:table-cell padding="1px" border="1px solid black"><fo:block></fo:block></fo:table-cell>									
								</fo:table-row>
							</fo:table-body>
						</fo:table>
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
</xsl:stylesheet>