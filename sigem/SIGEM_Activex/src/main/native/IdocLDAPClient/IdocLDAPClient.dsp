# Microsoft Developer Studio Project File - Name="IdocLDAPClient" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 6.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Dynamic-Link Library" 0x0102

CFG=IdocLDAPClient - Win32 Debug
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE use the Export Makefile command and run
!MESSAGE 
!MESSAGE NMAKE /f "IdocLDAPClient.mak".
!MESSAGE 
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE 
!MESSAGE NMAKE /f "IdocLDAPClient.mak" CFG="IdocLDAPClient - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "IdocLDAPClient - Win32 Debug" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE "IdocLDAPClient - Win32 Release MinDependency" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE 

# Begin Project
# PROP AllowPerConfigDependencies 0
# PROP Scc_ProjName ""$/invesDoc/COM/IdocLDAPClient", QPIAAAAA"
# PROP Scc_LocalPath "."
CPP=cl.exe
MTL=midl.exe
RSC=rc.exe

!IF  "$(CFG)" == "IdocLDAPClient - Win32 Debug"

# PROP BASE Use_MFC 2
# PROP BASE Use_Debug_Libraries 1
# PROP BASE Output_Dir "Debug"
# PROP BASE Intermediate_Dir "Debug"
# PROP BASE Target_Dir ""
# PROP Use_MFC 2
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "Deb"
# PROP Intermediate_Dir "Deb"
# PROP Ignore_Export_Lib 0
# PROP Target_Dir ""
# ADD BASE CPP /nologo /MDd /W3 /Gm /GX /ZI /Od /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_WINDLL" /D "_AFXDLL" /Yu"stdafx.h" /FD /GZ /c
# ADD CPP /nologo /MDd /W3 /Gm /GX /ZI /Od /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_WINDLL" /D "_AFXDLL" /D "_MBCS" /D "_USRDLL" /Yu"stdafx.h" /FD /GZ /c
# ADD BASE MTL /nologo /D "_DEBUG" /mktyplib203 /win32
# ADD BASE RSC /l 0xc0a /d "_DEBUG" /d "_AFXDLL"
# ADD RSC /l 0xc0a /d "_DEBUG" /d "_AFXDLL"
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LINK32=link.exe
# ADD BASE LINK32 /nologo /subsystem:windows /dll /debug /machine:I386 /pdbtype:sept
# ADD LINK32 Ntdsapi.lib Secur32.lib /nologo /subsystem:windows /dll /debug /machine:I386 /out:"..\..\iDescommon\dll\deb\IdocLDAPClientd.dll" /pdbtype:sept
# Begin Custom Build - Performing registration
OutDir=.\Deb
TargetPath=\invesDocWeb\iDescommon\dll\deb\IdocLDAPClientd.dll
InputPath=\invesDocWeb\iDescommon\dll\deb\IdocLDAPClientd.dll
SOURCE="$(InputPath)"

"$(OutDir)\regsvr32.trg" : $(SOURCE) "$(INTDIR)" "$(OUTDIR)"
	regsvr32 /s /c "$(TargetPath)" 
	echo regsvr32 exec. time > "$(OutDir)\regsvr32.trg" 
	
# End Custom Build

!ELSEIF  "$(CFG)" == "IdocLDAPClient - Win32 Release MinDependency"

# PROP BASE Use_MFC 2
# PROP BASE Use_Debug_Libraries 0
# PROP BASE Output_Dir "ReleaseMinDependency"
# PROP BASE Intermediate_Dir "ReleaseMinDependency"
# PROP BASE Target_Dir ""
# PROP Use_MFC 2
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "Rel"
# PROP Intermediate_Dir "Rel"
# PROP Ignore_Export_Lib 0
# PROP Target_Dir ""
# ADD BASE CPP /nologo /MD /W3 /GX /O1 /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_WINDLL" /D "_AFXDLL" /D "_MBCS" /D "_USRDLL" /Yu"stdafx.h" /FD /c
# ADD CPP /nologo /MD /W3 /GX /O1 /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_WINDLL" /D "_AFXDLL" /D "_MBCS" /D "_USRDLL" /D "_ATL_STATIC_REGISTRY" /Yu"stdafx.h" /FD /c
# ADD BASE MTL /nologo /D "NDEBUG" /mktyplib203 /win32
# ADD BASE RSC /l 0xc0a /d "NDEBUG" /d "_AFXDLL"
# ADD RSC /l 0xc0a /d "NDEBUG" /d "_AFXDLL"
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LINK32=link.exe
# ADD BASE LINK32 /nologo /subsystem:windows /dll /machine:I386
# ADD LINK32 Ntdsapi.lib Secur32.lib /nologo /subsystem:windows /dll /machine:I386 /out:"..\..\iDescommon\dll\rel\IdocLDAPClient.dll"
# Begin Custom Build - Performing registration
OutDir=.\Rel
TargetPath=\invesDocWeb\iDescommon\dll\rel\IdocLDAPClient.dll
InputPath=\invesDocWeb\iDescommon\dll\rel\IdocLDAPClient.dll
SOURCE="$(InputPath)"

"$(OutDir)\regsvr32.trg" : $(SOURCE) "$(INTDIR)" "$(OUTDIR)"
	regsvr32 /s /c "$(TargetPath)" 
	echo regsvr32 exec. time > "$(OutDir)\regsvr32.trg" 
	
# End Custom Build

!ENDIF 

# Begin Target

# Name "IdocLDAPClient - Win32 Debug"
# Name "IdocLDAPClient - Win32 Release MinDependency"
# Begin Group "Source Files"

# PROP Default_Filter "cpp;c;cxx;rc;def;r;odl;idl;hpj;bat"
# Begin Source File

SOURCE=.\AXSafe.cpp
# End Source File
# Begin Source File

SOURCE=.\Error.cpp
# End Source File
# Begin Source File

SOURCE=.\IdocLDAP.cpp
# End Source File
# Begin Source File

SOURCE=.\IdocLDAPClient.cpp
# End Source File
# Begin Source File

SOURCE=.\IdocLDAPClient.def

!IF  "$(CFG)" == "IdocLDAPClient - Win32 Debug"

# PROP Exclude_From_Build 1

!ELSEIF  "$(CFG)" == "IdocLDAPClient - Win32 Release MinDependency"

!ENDIF 

# End Source File
# Begin Source File

SOURCE=.\IdocLDAPClient.idl
# ADD MTL /tlb ".\IdocLDAPClient.tlb" /h "IdocLDAPClient.h" /iid "IdocLDAPClient_i.c" /Oicf
# End Source File
# Begin Source File

SOURCE=.\IdocLDAPClient.rc
# End Source File
# Begin Source File

SOURCE=.\IdocLDAPClientd.def

!IF  "$(CFG)" == "IdocLDAPClient - Win32 Debug"

!ELSEIF  "$(CFG)" == "IdocLDAPClient - Win32 Release MinDependency"

# PROP Exclude_From_Build 1

!ENDIF 

# End Source File
# Begin Source File

SOURCE=.\StdAfx.cpp
# ADD CPP /Yc"stdafx.h"
# End Source File
# End Group
# Begin Group "Header Files"

# PROP Default_Filter "h;hpp;hxx;hm;inl"
# Begin Source File

SOURCE=.\AXSafe.h
# End Source File
# Begin Source File

SOURCE=.\Error.h
# End Source File
# Begin Source File

SOURCE=.\IdocLDAP.h
# End Source File
# Begin Source File

SOURCE=.\Resource.h
# End Source File
# Begin Source File

SOURCE=.\StdAfx.h
# End Source File
# End Group
# Begin Group "Resource Files"

# PROP Default_Filter "ico;cur;bmp;dlg;rc2;rct;bin;rgs;gif;jpg;jpeg;jpe"
# Begin Source File

SOURCE=.\IdocLDAP.rgs
# End Source File
# End Group
# End Target
# End Project
