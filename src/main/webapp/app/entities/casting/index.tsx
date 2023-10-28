import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Casting from './casting';
import CastingDetail from './casting-detail';
import CastingUpdate from './casting-update';
import CastingDeleteDialog from './casting-delete-dialog';

const CastingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Casting />} />
    <Route path="new" element={<CastingUpdate />} />
    <Route path=":id">
      <Route index element={<CastingDetail />} />
      <Route path="edit" element={<CastingUpdate />} />
      <Route path="delete" element={<CastingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CastingRoutes;
