# Microsoft Developer Studio Project File - Name="iDocPost" - Package Owner=<4>
# Microsoft Developer Studio Generated Build File, Format Version 6.00
# ** DO NOT EDIT **

# TARGTYPE "Win32 (x86) Dynamic-Link Library" 0x0102

CFG=iDocPost - Win32 Debug
!MESSAGE This is not a valid makefile. To build this project using NMAKE,
!MESSAGE use the Export Makefile command and run
!MESSAGE 
!MESSAGE NMAKE /f "iDocPost.mak".
!MESSAGE 
!MESSAGE You can specify a configuration when running NMAKE
!MESSAGE by defining the macro CFG on the command line. For example:
!MESSAGE 
!MESSAGE NMAKE /f "iDocPost.mak" CFG="iDocPost - Win32 Debug"
!MESSAGE 
!MESSAGE Possible choices for configuration are:
!MESSAGE 
!MESSAGE "iDocPost - Win32 Debug" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE "iDocPost - Win32 Release" (based on "Win32 (x86) Dynamic-Link Library")
!MESSAGE 

# Begin Project
# PROP AllowPerConfigDependencies 0
# PROP Scc_ProjName ""$/LexNet/iDocPost", UTEAAAAA"
# PROP Scc_LocalPath "."
CPP=cl.exe
MTL=midl.exe
RSC=rc.exe

!IF  "$(CFG)" == "iDocPost - Win32 Debug"

# PROP BASE Use_MFC 2
# PROP BASE Use_Debug_Libraries 1
# PROP BASE Output_Dir "Debug"
# PROP BASE Intermediate_Dir "Debug"
# PROP BASE Target_Dir ""
# PROP Use_MFC 2
# PROP Use_Debug_Libraries 1
# PROP Output_Dir "..\bin\Debug"
# PROP Intermediate_Dir "..\temp\iDocPost\Debug"
# PROP Ignore_Export_Lib 0
# PROP Target_Dir ""
# ADD BASE CPP /nologo /MDd /W3 /Gm /GX /ZI /Od /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_WINDLL" /D "_AFXDLL" /Yu"stdafx.h" /FD /GZ /c
# ADD CPP /nologo /MDd /W4 /GX /Z7 /Od /I "..\include" /D "WIN32" /D "_DEBUG" /D "_WINDOWS" /D "_WINDLL" /D "_AFXDLL" /D "_MBCS" /D "_USRDLL" /Yu"stdafx.h" /FD /GZ /c
# SUBTRACT CPP /Gy /Fr
# ADD BASE MTL /nologo /D "_DEBUG" /mktyplib203 /win32
# ADD BASE RSC /l 0x409 /d "_DEBUG" /d "_AFXDLL"
# ADD RSC /l 0x409 /d "_DEBUG" /d "_AFXDLL"
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LINK32=link.exe
# ADD BASE LINK32 /nologo /subsystem:windows /dll /debug /machine:I386 /pdbtype:sept
# ADD LINK32 lnutild.lib wininet.lib rpcrt4.lib urlmon.lib Shlwapi.lib /nologo /subsystem:windows /dll /pdb:none /debug /machine:I386 /def:".\iDocPost.def" /out:"..\bin\debug/iDocPostD.dll" /libpath:"..\lib" /libpath:"..\bin\Debug" /OPT:REF
# Begin Custom Build - Performing registration
OutDir=.\..\bin\Debug
TargetPath=\invesDocWeb\PLUG\bin\debug\iDocPostD.dll
InputPath=\invesDocWeb\PLUG\bin\debug\iDocPostD.dll
SOURCE="$(InputPath)"

"$(OutDir)\regsvr32.trg" : $(SOURCE) "$(INTDIR)" "$(OUTDIR)"
	regsvr32 /s /c "$(TargetPath)" 
	echo regsvr32 exec. time > "$(OutDir)\regsvr32.trg" 
	
# End Custom Build

!ELSEIF  "$(CFG)" == "iDocPost - Win32 Release"

# PROP BASE Use_MFC 2
# PROP BASE Use_Debug_Libraries 0
# PROP BASE Output_Dir "iDocPost___Win32_Release"
# PROP BASE Intermediate_Dir "iDocPost___Win32_Release"
# PROP BASE Ignore_Export_Lib 0
# PROP BASE Target_Dir ""
# PROP Use_MFC 2
# PROP Use_Debug_Libraries 0
# PROP Output_Dir "..\bin\Release"
# PROP Intermediate_Dir "..\temp\iDocPost\Release"
# PROP Ignore_Export_Lib 0
# PROP Target_Dir ""
# ADD BASE CPP /nologo /MD /W4 /GX /O1 /I "..\include" /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_WINDLL" /D "_AFXDLL" /D "_MBCS" /D "_USRDLL" /D "_ATL_STATIC_REGISTRY" /FR /Yu"stdafx.h" /FD /c
# ADD CPP /nologo /MD /W4 /GX /O1 /I "..\include" /D "WIN32" /D "NDEBUG" /D "_WINDOWS" /D "_WINDLL" /D "_AFXDLL" /D "_MBCS" /D "_USRDLL" /D "_ATL_STATIC_REGISTRY" /Yu"stdafx.h" /FD /c
# SUBTRACT CPP /Fr
# ADD BASE RSC /l 0x409 /d "NDEBUG" /d "_AFXDLL"
# ADD RSC /l 0x409 /d "NDEBUG" /d "_AFXDLL"
BSC32=bscmake.exe
# ADD BASE BSC32 /nologo
# ADD BSC32 /nologo
LINK32=link.exe
# ADD BASE LINK32 lnutil.lib wininet.lib rpcrt4.lib /nologo /subsystem:windows /dll /machine:I386 /libpath:"..\lib"
# ADD LINK32 lnutil.lib wininet.lib rpcrt4.lib urlmon.lib Shlwapi.lib /nologo /subsystem:windows /dll /pdb:none /machine:I386 /libpath:"..\lib" /libpath:"..\bin\Release" /opt:ref
# Begin Custom Build - Performing registration
OutDir=.\..\bin\Release
TargetPath=\invesDocWeb\PLUG\bin\Release\iDocPost.dll
InputPath=\invesDocWeb\PLUG\bin\Release\iDocPost.dll
SOURCE="$(InputPath)"

"$(OutDir)\regsvr32.trg" : $(SOURCE) "$(INTDIR)" "$(OUTDIR)"
	regsvr32 /s /c "$(TargetPath)" 
	echo regsvr32 exec. time > "$(OutDir)\regsvr32.trg" 
	
# End Custom Build

!ENDIF 

# Begin Target

# Name "iDocPost - Win32 Debug"
# Name "iDocPost - Win32 Release"
# Begin Group "Source Files"

# PROP Default_Filter "cpp;c;cxx;rc;def;r;odl;idl;hpj;bat"
# Begin Source File

SOURCE=.\BrowseDirectory.cpp
# End Source File
# Begin Source File

SOURCE=.\iDocPost.cpp
# End Source File
# Begin Source File

SOURCE=.\iDocPost.def

!IF  "$(CFG)" == "iDocPost - Win32 Debug"

# PROP Exclude_From_Build 1

!ELSEIF  "$(CFG)" == "iDocPost - Win32 Release"

!ENDIF 

# End Source File
# Begin Source File

SOURCE=.\iDocPost.idl
# ADD MTL /tlb ".\iDocPost.tlb" /h "iDocPost.h" /iid "iDocPost_i.c" /Oicf
# End Source File
# Begin Source File

SOURCE=.\iDocPost.rc
# End Source File
# Begin Source File

SOURCE=.\ProxyDlg.cpp
# End Source File
# Begin Source File

SOURCE=.\StdAfx.cpp
# ADD CPP /Yc"stdafx.h"
# End Source File
# Begin Source File

SOURCE=.\Upload.cpp
# End Source File
# End Group
# Begin Group "Header Files"

# PROP Default_Filter "h;hpp;hxx;hm;inl"
# Begin Source File

SOURCE=.\BrowseDirectory.h
# End Source File
# Begin Source File

SOURCE=.\ProxyDlg.h
# End Source File
# Begin Source File

SOURCE=.\Resource.h
# End Source File
# Begin Source File

SOURCE=.\StdAfx.h
# End Source File
# Begin Source File

SOURCE=.\Upload.h
# End Source File
# End Group
# Begin Group "Resource Files"

# PROP Default_Filter "ico;cur;bmp;dlg;rc2;rct;bin;rgs;gif;jpg;jpeg;jpe"
# Begin Source File

SOURCE=.\Upload.rgs
# End Source File
# End Group
# End Target
# End Project
