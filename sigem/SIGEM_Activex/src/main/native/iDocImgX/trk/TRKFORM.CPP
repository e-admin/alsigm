
#include <afxwin.h>
#include <afxext.h>
#include "trk.h"

#ifdef _DEBUG
#undef THIS_FILE
static char BASED_CODE THIS_FILE[] = __FILE__;
#endif

IMPLEMENT_SERIAL(CTrkObjForm,CObject,0)

CTrkObjForm::CTrkObjForm()
{
   m_pExtObj     = NULL;
   m_pIntObjList = NULL;
}

CTrkObjForm::CTrkObjForm(CTrkObj* pExtObj,CTrkObjList* pIntObjList)
{
   m_pExtObj     = pExtObj;
   m_pIntObjList = pIntObjList;
   Syncr();
}

void CTrkObjForm::Init(LONG ObjClassId)
{
   InitExtObj(ObjClassId);
   InitIntObjList();
   Syncr();
}

CTrkObjForm::~CTrkObjForm()
{
   delete m_pExtObj;
   delete m_pIntObjList;
}

void CTrkObjForm::Serialize(CArchive& Arch)
{
   CObject::Serialize(Arch);
   m_pExtObj->Serialize(Arch);
   m_pIntObjList->Serialize(Arch);
}

void CTrkObjForm::OnDraw(CDC* pDC,double Zoom) const
{
   m_pExtObj->OnDraw(pDC,Zoom);
   m_pIntObjList->OnDraw(pDC,Zoom);
}

BOOL CTrkObjForm::OnSetCursor(CWnd* pWnd,UINT HitTest,
                              UINT Msg,double Zoom) const
{

   BOOL Set = FALSE;

   Set = m_pIntObjList->OnSetCursor(pWnd,HitTest,Msg,Zoom);

   if ( !Set )
      Set = m_pExtObj->OnSetCursor(pWnd,HitTest,Msg,Zoom);

   return(Set);

}

BOOL CTrkObjForm::OnLButtonDown(LONG ObjClassId,POINT Point,UINT Flags,
                                CWnd* pWnd,double Zoom,int UpdateMode)
{

   BOOL Changed = FALSE;
   int  HC;

   if ( m_pExtObj->IsSelected() &&
        m_pExtObj->IsHit(Point,pWnd,HC,Zoom) )
   {
      if (HC != CTrk::HC_Middle)
      {
         Changed = m_pExtObj->OnLButtonDown(Point,Flags,pWnd,
                                            Zoom,UpdateMode);
         if (Changed)
            OnExtObjChanged();
         goto End;
      }
   }

   m_pExtObj->Unselect(pWnd,Zoom,UpdateMode);

   Changed = m_pIntObjList->OnLButtonDown(ObjClassId,Point,Flags,
                                          pWnd,Zoom,UpdateMode);

   if (Changed)
      OnIntObjListChanged();

   if (m_pIntObjList->GetCountSel() < 1)
      m_pExtObj->Select(pWnd,Zoom,UpdateMode);

   End:

   return(Changed);

}

void CTrkObjForm::OnLButtonDblClk(POINT Point,UINT Flags,
                                  CWnd* pWnd,double Zoom)
{

   int HC;

   CTrkObj* pObj = m_pIntObjList->GetHitObj(Point,pWnd,HC,Zoom);

   if (pObj == NULL)
   {
      if ( m_pExtObj->IsHit(Point,pWnd,HC,Zoom) )
         m_pExtObj->OnLButtonDblClk(Point,Flags,pWnd,Zoom);
   }
   else
      m_pIntObjList->OnLButtonDblClk(Point,Flags,pWnd,Zoom);

}

void CTrkObjForm::OnEditClear(CWnd* pWnd,double Zoom,int UpdateMode)
{
   m_pIntObjList->OnEditClear(pWnd,Zoom,UpdateMode);
}

void CTrkObjForm::OnUpdateEditClear(CCmdUI* pCmdUI) const
{
   m_pIntObjList->OnUpdateEditClear(pCmdUI);
}

void CTrkObjForm::OnAlign(int AlignMode,CWnd* pWnd,double Zoom,
                          int UpdateMode)
{
   m_pIntObjList->OnAlign(AlignMode,pWnd,Zoom,UpdateMode);
}

void CTrkObjForm::OnUpdateAlign(CCmdUI* pCmdUI) const
{
   m_pIntObjList->OnUpdateAlign(pCmdUI);
}

void CTrkObjForm::OnSameSize(int SameSizeMode,CWnd* pWnd,double Zoom,
                             int UpdateMode)
{
   m_pIntObjList->OnSameSize(SameSizeMode,pWnd,Zoom,UpdateMode);
}

void CTrkObjForm::OnUpdateSameSize(CCmdUI* pCmdUI) const
{
   m_pIntObjList->OnUpdateSameSize(pCmdUI);
}

void CTrkObjForm::OnCenter(int CenterMode,CWnd* pWnd,double Zoom,
                           int UpdateMode)
{
   CRect ExtRect;
   m_pIntObjList->GetDefExtRect(ExtRect);
   m_pIntObjList->OnCenter(CenterMode,ExtRect,pWnd,Zoom,UpdateMode);
}

void CTrkObjForm::OnUpdateCenter(CCmdUI* pCmdUI) const
{
   m_pIntObjList->OnUpdateCenter(pCmdUI);
}

void CTrkObjForm::OnSameSpace(int SameSpaceMode,CWnd* pWnd,double Zoom,
                              int UpdateMode)
{
   m_pIntObjList->OnSameSpace(SameSpaceMode,pWnd,Zoom,UpdateMode);
}

void CTrkObjForm::OnUpdateSameSpace(CCmdUI* pCmdUI) const
{
   m_pIntObjList->OnUpdateSameSpace(pCmdUI);
}

void CTrkObjForm::OnSelectAll(CWnd* pWnd,double Zoom,
                              int UpdateMode)
{
   m_pIntObjList->OnSelectAll(pWnd,Zoom,UpdateMode);
}

void CTrkObjForm::OnUpdateSelectAll(CCmdUI* pCmdUI) const
{
   m_pIntObjList->OnUpdateSelectAll(pCmdUI);
}

void CTrkObjForm::Syncr()
{
   ChangeIntRect();
   ChangeExtRect();
}

void CTrkObjForm::InitExtObj(LONG ObjClassId)
{
   SetupExtObj(ObjClassId);
}

void CTrkObjForm::InitIntObjList()
{
   SetupIntObjList();
}

void CTrkObjForm::SetupExtObj(LONG ObjClassId)
{
   CreateExtObj(ObjClassId);
   SetExtObjDefaults();
}

void CTrkObjForm::CreateExtObj(LONG ObjClassId)
{
   m_pExtObj = new CTrkObj(ObjClassId);
}

void CTrkObjForm::SetExtObjDefaults()
{
}

void CTrkObjForm::SetupIntObjList()
{
   CreateIntObjList();
   SetIntObjListDefaults();
}

void CTrkObjForm::CreateIntObjList()
{
   m_pIntObjList = new CTrkObjList();
}

void CTrkObjForm::SetIntObjListDefaults()
{
}

void CTrkObjForm::OnExtObjChanged()
{
   ChangeExtRect();
}

void CTrkObjForm::OnIntObjListChanged()
{
   ChangeIntRect();
}

void CTrkObjForm::ChangeExtRect()
{
   CRect Rect;
   m_pExtObj->GetRect(Rect);
   m_pIntObjList->SetDefExtRect(Rect);
}

void CTrkObjForm::ChangeIntRect()
{
   CRect Rect;
   m_pIntObjList->GetBoundRect(Rect);
   m_pExtObj->SetIntRect(Rect);
}

