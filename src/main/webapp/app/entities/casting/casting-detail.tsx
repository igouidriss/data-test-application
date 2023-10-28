import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './casting.reducer';

export const CastingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const castingEntity = useAppSelector(state => state.casting.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="castingDetailsHeading">
          <Translate contentKey="dataTestApplicationApp.casting.detail.title">Casting</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{castingEntity.id}</dd>
          <dt>
            <Translate contentKey="dataTestApplicationApp.casting.movie">Movie</Translate>
          </dt>
          <dd>{castingEntity.movie ? castingEntity.movie.id : ''}</dd>
          <dt>
            <Translate contentKey="dataTestApplicationApp.casting.actors">Actors</Translate>
          </dt>
          <dd>
            {castingEntity.actors
              ? castingEntity.actors.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {castingEntity.actors && i === castingEntity.actors.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/casting" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/casting/${castingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CastingDetail;
