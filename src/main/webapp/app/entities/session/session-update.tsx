import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPeriod } from 'app/shared/model/period.model';
import { getEntities as getPeriods } from 'app/entities/period/period.reducer';
import { IMovie } from 'app/shared/model/movie.model';
import { getEntities as getMovies } from 'app/entities/movie/movie.reducer';
import { ISession } from 'app/shared/model/session.model';
import { getEntity, updateEntity, createEntity, reset } from './session.reducer';

export const SessionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const periods = useAppSelector(state => state.period.entities);
  const movies = useAppSelector(state => state.movie.entities);
  const sessionEntity = useAppSelector(state => state.session.entity);
  const loading = useAppSelector(state => state.session.loading);
  const updating = useAppSelector(state => state.session.updating);
  const updateSuccess = useAppSelector(state => state.session.updateSuccess);

  const handleClose = () => {
    navigate('/session');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getPeriods({}));
    dispatch(getMovies({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...sessionEntity,
      ...values,
      period: periods.find(it => it.id.toString() === values.period.toString()),
      movie: movies.find(it => it.id.toString() === values.movie.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...sessionEntity,
          period: sessionEntity?.period?.id,
          movie: sessionEntity?.movie?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dataTestApplicationApp.session.home.createOrEditLabel" data-cy="SessionCreateUpdateHeading">
            <Translate contentKey="dataTestApplicationApp.session.home.createOrEditLabel">Create or edit a Session</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="session-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('dataTestApplicationApp.session.startHour')}
                id="session-startHour"
                name="startHour"
                data-cy="startHour"
                type="text"
              />
              <ValidatedField
                id="session-period"
                name="period"
                data-cy="period"
                label={translate('dataTestApplicationApp.session.period')}
                type="select"
              >
                <option value="" key="0" />
                {periods
                  ? periods.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="session-movie"
                name="movie"
                data-cy="movie"
                label={translate('dataTestApplicationApp.session.movie')}
                type="select"
              >
                <option value="" key="0" />
                {movies
                  ? movies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/session" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default SessionUpdate;
