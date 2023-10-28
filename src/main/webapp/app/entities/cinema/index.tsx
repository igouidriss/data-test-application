import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Cinema from './cinema';
import CinemaDetail from './cinema-detail';
import CinemaUpdate from './cinema-update';
import CinemaDeleteDialog from './cinema-delete-dialog';

const CinemaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Cinema />} />
    <Route path="new" element={<CinemaUpdate />} />
    <Route path=":id">
      <Route index element={<CinemaDetail />} />
      <Route path="edit" element={<CinemaUpdate />} />
      <Route path="delete" element={<CinemaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CinemaRoutes;
