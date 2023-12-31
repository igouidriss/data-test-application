import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './room.reducer';

export const RoomDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const roomEntity = useAppSelector(state => state.room.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="roomDetailsHeading">
          <Translate contentKey="dataTestApplicationApp.room.detail.title">Room</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{roomEntity.id}</dd>
          <dt>
            <span id="seatsNumber">
              <Translate contentKey="dataTestApplicationApp.room.seatsNumber">Seats Number</Translate>
            </span>
          </dt>
          <dd>{roomEntity.seatsNumber}</dd>
          <dt>
            <span id="screenHeight">
              <Translate contentKey="dataTestApplicationApp.room.screenHeight">Screen Height</Translate>
            </span>
          </dt>
          <dd>{roomEntity.screenHeight}</dd>
          <dt>
            <span id="screenWidth">
              <Translate contentKey="dataTestApplicationApp.room.screenWidth">Screen Width</Translate>
            </span>
          </dt>
          <dd>{roomEntity.screenWidth}</dd>
          <dt>
            <span id="distance">
              <Translate contentKey="dataTestApplicationApp.room.distance">Distance</Translate>
            </span>
          </dt>
          <dd>{roomEntity.distance}</dd>
          <dt>
            <Translate contentKey="dataTestApplicationApp.room.cinema">Cinema</Translate>
          </dt>
          <dd>{roomEntity.cinema ? roomEntity.cinema.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/room" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/room/${roomEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RoomDetail;
