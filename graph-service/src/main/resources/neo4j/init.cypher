CREATE INDEX user_id_index FOR (u:User) ON (u.user_id);
CREATE INDEX friends_revision_index FOR (f:FRIENDS_SIMILARITY) ON (f.friends_revision);

CREATE INDEX img_thematic_revision_index FOR (u:User) ON (u.img_thematic_revision);
CREATE INDEX group_thematic_revision_index FOR (u:User) ON (u.group_thematic_revision);

CREATE INDEX img_revision_index FOR (f:IMG_SIMILARITY) ON (f.img_revision);
CREATE INDEX group_revision_index FOR (f:GROUP_SIMILARITY) ON (f.group_revision);