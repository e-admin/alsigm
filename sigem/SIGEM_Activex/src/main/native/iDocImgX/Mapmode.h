
#ifndef __IMAPMODE_H__
#define __IMAPMODE_H__

void LogToDev(LPPOINT pPoint,CDC* pDC,double Zoom = 1);
void LogToDev(LPPOINT pPoint,CWnd* pWnd,double Zoom = 1);
void LogToDev(LPSIZE pSize,CDC* pDC,double Zoom = 1);
void LogToDev(LPSIZE pSize,CWnd* pWnd,double Zoom = 1);
void LogToDev(LPRECT pRect,CDC* pDC,double Zoom = 1);
void LogToDev(LPRECT pRect,CWnd* pWnd,double Zoom = 1);

void DevToLog(LPPOINT pPoint,CDC* pDC,double Zoom = 1);
void DevToLog(LPPOINT pPoint,CWnd* pWnd,double Zoom = 1);
void DevToLog(LPSIZE pSize,CDC* pDC,double Zoom = 1);
void DevToLog(LPSIZE pSize,CWnd* pWnd,double Zoom = 1);
void DevToLog(LPRECT pRect,CDC* pDC,double Zoom = 1);
void DevToLog(LPRECT pRect,CWnd* pWnd,double Zoom = 1);

#endif // __IMAPMODE_H__

