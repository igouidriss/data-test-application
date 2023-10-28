import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/address">
        <Translate contentKey="global.menu.entities.address" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/operator">
        <Translate contentKey="global.menu.entities.operator" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/cinema">
        <Translate contentKey="global.menu.entities.cinema" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/room">
        <Translate contentKey="global.menu.entities.room" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/period">
        <Translate contentKey="global.menu.entities.period" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/session">
        <Translate contentKey="global.menu.entities.session" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/movie">
        <Translate contentKey="global.menu.entities.movie" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/casting">
        <Translate contentKey="global.menu.entities.casting" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/actor">
        <Translate contentKey="global.menu.entities.actor" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
