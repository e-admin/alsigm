@echo off
rem
rem
if "%1" == "l" goto l
if "%1" == "L" goto l
if "%1" == "n" goto n
if "%1" == "N" goto n
goto error
rem
:l
if "%2" == "d" goto ld
if "%2" == "D" goto ld
if "%2" == "r" goto lr
if "%2" == "R" goto lr
goto error
rem
:n
if "%2" == "d" goto nd
if "%2" == "D" goto nd
if "%2" == "r" goto nr
if "%2" == "R" goto nr
goto error
rem
rem
:ld
echo local debug
rem ********** LOCAL DEBUG **********
copy trk.h   c:\invesimd\inc
copy trk.lib c:\invesimd\lib\deb
rem **********
goto end
rem
rem
:lr
echo local release
rem ********** LOCAL RELEASE **********
copy trk.h   c:\invesimd\inc
copy trk.lib c:\invesimd\lib\rel
rem **********
goto end
rem
rem
:nd
echo net debug
rem ********** NET DEBUG **********
copy trk.h   n:\invesimd\inc
copy trk.lib n:\invesimd\lib\deb
copy *.cpp   n:\trk
copy *.h     n:\trk
copy *.pdb   n:\trk
rem **********
goto end
rem
rem
:nr
echo net release
rem ********** NET RELEASE **********
copy trk.h   n:\invesimd\inc
copy trk.lib n:\invesimd\lib\rel
rem **********
goto end
rem
rem
:error
echo Usage: export l d
echo        export l r
echo        export n d
echo        export n r
echo Done : export l d
goto ld
rem
rem
:end
