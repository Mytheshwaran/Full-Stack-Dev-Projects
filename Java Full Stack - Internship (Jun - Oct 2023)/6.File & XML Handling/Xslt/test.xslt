<?xml version="1.0" encoding="UTF-8"?>
<html xsl:version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<body>
		<h1>Student Details</h1>
		<xsl:for-each select="Students/Student">
			<ul>
				<xsl:value-of select="name" />
			</ul>
			<ul>
				<xsl:value-of select="age" />
			</ul>
		</xsl:for-each>
	</body>
</html>
