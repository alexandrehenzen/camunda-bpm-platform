-- CAM-7737

ALTER TABLE ACT_HI_VARINST
  ADD STATE_ nvarchar(20);

-- semantic version --
ALTER TABLE ACT_RE_DECISION_DEF
  VERSION_TAG_ nvarchar(64);
