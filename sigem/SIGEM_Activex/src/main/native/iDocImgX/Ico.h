
#ifndef __ICO_H__
#define __ICO_H__

#define MAX_FILE_NAME_LEN   128

class ICIco 
{

   public:

   ICIco();
   virtual ~ICIco();

   virtual LONG Init(LPCSTR IcoFileName);
   virtual LONG Term();

   virtual LONG Draw(CDC* pDC,LPCRECT pImgRect,LPCRECT pDCRect) const;

   LONG GetDims(CSize& ImgSize) const;
   LONG GetDims(int& W,int& H) const;
	

   protected:
   
   virtual LONG SetCropRect(LPCRECT pImgZRect,BOOL XOR) const;

   protected:

   HIGEAR  m_hAccAND;
   HIGEAR  m_hIGear;
   char    m_FileName[MAX_FILE_NAME_LEN];   
   AT_MODE m_FileType;
	
};



#endif // __ICO_H__

