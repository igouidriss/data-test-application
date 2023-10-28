import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './session.reducer';

export const SessionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const sessionEntity = useAppSelector(state => state.session.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sessionDetailsHeading">
          <Translate contentKey="dataTestApplicationApp.session.detail.title">Session</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{sessionEntity.id}</dd>
          <dt>
            <span id="startHour">
              <Translate contentKey="dataTestApplicationApp.session.startHour">Start Hour</Translate>
            </span>
          </dt>
          <dd>{sessionEntity.startHour}</dd>
          <dt>
            <Translate contentKey="dataTestApplicationApp.session.period">Period</Translate>
          </dt>
          <dd>{sessionEntity.period ? sessionEntity.period.id : ''}</dd>
          <dt>
            <Translate contentKey="dataTestApplicationApp.session.movie">Movie</Translate>
          </dt>
          <dd>{sessionEntity.movie ? sessionEntity.movie.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/session" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/session/${sessionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SessionDetail;
