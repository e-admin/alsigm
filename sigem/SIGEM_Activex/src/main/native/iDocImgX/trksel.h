#ifndef __TRKSEL_H__
#define __TRKSEL_H__

class CSelGearTrk: public CTrk
{
   DECLARE_SERIAL(CSelGearTrk);
   public:

   CSelGearTrk();
   
   CView* GetParentView();
   void SetParentView(CView* pWnd);

   virtual ~CSelGearTrk();   

   virtual void Serialize(CArchive& Arch);   
   
   virtual void LogToDev(LPPOINT pPoint,CDC* pDC,double Zoom= 1) const;
   virtual void LogToDev(LPPOINT pPoint,CWnd* pWnd,double Zoom= 1) const;
   virtual void LogToDev(LPSIZE pSize,CDC* pDC,double Zoom= 1) const;
   virtual void LogToDev(LPSIZE pSize,CWnd* pWnd,double Zoom= 1) const;
   virtual void LogToDev(LPRECT pRect,CDC* pDC,double Zoom= 1) const;
   virtual void LogToDev(LPRECT pRect,CWnd* pWnd,double Zoom= 1) const;
   virtual void DevToLog(LPPOINT pPoint,CDC* pDC,double Zoom= 1) const;
   virtual void DevToLog(LPPOINT pPoint,CWnd* pWnd,double Zoom= 1) const;  
   virtual void DevToLog(LPRECT pRect,CDC* pDC,double Zoom= 1) const;
   virtual void DevToLog(LPRECT pRect,CWnd* pWnd,double Zoom= 1) const;

   
   CPoint DCToImg(CPoint point) const;
   CPoint ImgToDC(CPoint point) const;

   public:   ////Cambiar a protected
      CView* m_pWnd;

};

#endif // __TRKSEL_H__