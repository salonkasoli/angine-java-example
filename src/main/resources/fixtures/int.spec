interface Entity {
  abstract id: str;
}

interface UrlEntity <: Entity {
  path: str;
}

interface Subject <: Entity {
  role: str;
}
