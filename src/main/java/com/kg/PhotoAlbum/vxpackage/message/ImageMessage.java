package com.kg.PhotoAlbum.vxpackage.message;

import com.kg.PhotoAlbum.vxpackage.model.Image;

/**
 * @ClassName ImageMessage
 * @Describe TODO
 * @Autor OnlyMyself
 * @Date 2019-05-23 17:46
 */
public class ImageMessage extends BaseMessage{
      private Image  Image;//Image节点

      public Image getImage() {
            return Image;
      }

      public void setImage(Image image) {
            Image = image;
      }

}
