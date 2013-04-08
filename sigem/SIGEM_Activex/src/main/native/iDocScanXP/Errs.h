
#ifndef __IDOCSCANXP_ERRS_H__
#define __IDOCSCANXP_ERRS_H__

#if _MSC_VER >= 1000
#pragma once
#endif // _MSC_VER >= 1000

//////////////////////////////////////////////////////////////////////
//                                                                  //
//  iDocScanXP Errs                                                  //
//                                                                  //
//////////////////////////////////////////////////////////////////////

#define IDOCSCANX_FIRST_ERROR 850000L

#define IDOCSCANX_ERR_MEM              IDOCSCANX_FIRST_ERROR + 1L
#define IDOCSCANX_ERR_CREATE_OBJECT    IDOCSCANX_FIRST_ERROR + 2L
#define IDOCSCANX_ERR_COM              IDOCSCANX_FIRST_ERROR + 3L
#define IDOCSCANX_ERR_FILE             IDOCSCANX_FIRST_ERROR + 4L
#define IDOCSCANX_ERR_NOT_INITIALIZED  IDOCSCANX_FIRST_ERROR + 5L
#define IDOCSCANX_ERR_PARAM_NO_VALID   IDOCSCANX_FIRST_ERROR + 6L

#endif // __IDOCSCANXP_ERRS_H__

