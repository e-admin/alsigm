
#ifndef __IDOCIMGX_ERRS_H__
#define __IDOCIMGX_ERRS_H__

#if _MSC_VER >= 1000
#pragma once
#endif // _MSC_VER >= 1000

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  iDocImgX Errs                                                   //
//                                                                  //
//////////////////////////////////////////////////////////////////////

#define IDOCIMGX_FIRST_ERROR 30000L

#define IDOCIMGX_ERR_FILE_NOFOUND    IDOCIMGX_FIRST_ERROR + 1L        
#define IDOCIMGX_ERR_STARTPAGE       IDOCIMGX_FIRST_ERROR + 2L
#define IDOCIMGX_ERR_NOT_INITPRINT   IDOCIMGX_FIRST_ERROR + 3L
#define IDOCIMGX_ERR_INITPRINT       IDOCIMGX_FIRST_ERROR + 4L 
#define IDOCIMGX_ERR_ALREADY_INIT    IDOCIMGX_FIRST_ERROR + 5L
#define IDOCIMGX_ERR_SAVEFILE        IDOCIMGX_FIRST_ERROR + 6L
#define IDOCIMGX_ERR_NO_SELECTION    IDOCIMGX_FIRST_ERROR + 7L
#define IDOCIMGX_ERR_FILE            IDOCIMGX_FIRST_ERROR + 8L
#define IDOCIMGX_ERR_CREATE_TEMP_DIR IDOCIMGX_FIRST_ERROR + 9L

#endif // __IDOCIMGX_ERRS_H__

