package com.foo.fileutil

package policy {
  trait Policy
  trait MandatoryFieldsPolicy extends Policy {
    val mandatoryColumnIndex: List[Int]
  }
  
  trait DiscountTypeAllowed extends Policy {
    val allowedDiscountTypes: List[String]
  }
}